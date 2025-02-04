/*
 * Copyright (c) 2019 ETH Zürich, Educational Development and Technology (LET)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ch.ethz.seb.sebserver.webservice.servicelayer.session.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ch.ethz.seb.sebserver.gbl.Constants;
import ch.ethz.seb.sebserver.gbl.model.exam.Exam;
import ch.ethz.seb.sebserver.gbl.model.exam.Exam.ExamStatus;
import ch.ethz.seb.sebserver.gbl.model.exam.QuizData;
import ch.ethz.seb.sebserver.gbl.model.institution.LmsSetup.LmsType;
import ch.ethz.seb.sebserver.gbl.profile.WebServiceProfile;
import ch.ethz.seb.sebserver.gbl.util.Result;
import ch.ethz.seb.sebserver.gbl.util.Utils;
import ch.ethz.seb.sebserver.webservice.WebserviceInfo;
import ch.ethz.seb.sebserver.webservice.servicelayer.dao.ExamDAO;
import ch.ethz.seb.sebserver.webservice.servicelayer.dao.FilterMap;
import ch.ethz.seb.sebserver.webservice.servicelayer.lms.LmsAPIService;
import ch.ethz.seb.sebserver.webservice.servicelayer.lms.LmsAPITemplate;
import ch.ethz.seb.sebserver.webservice.servicelayer.lms.SEBRestrictionService;
import ch.ethz.seb.sebserver.webservice.servicelayer.lms.impl.moodle.legacy.MoodleCourseAccess;
import ch.ethz.seb.sebserver.webservice.servicelayer.session.ExamFinishedEvent;
import ch.ethz.seb.sebserver.webservice.servicelayer.session.ExamResetEvent;
import ch.ethz.seb.sebserver.webservice.servicelayer.session.ExamStartedEvent;

@Lazy
@Service
@WebServiceProfile
class ExamUpdateHandler {

    private static final Logger log = LoggerFactory.getLogger(ExamUpdateHandler.class);

    private final ExamDAO examDAO;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SEBRestrictionService sebRestrictionService;
    private final LmsAPIService lmsAPIService;
    private final String updatePrefix;
    private final Long examTimeSuffix;

    public ExamUpdateHandler(
            final ExamDAO examDAO,
            final ApplicationEventPublisher applicationEventPublisher,
            final SEBRestrictionService sebRestrictionService,
            final LmsAPIService lmsAPIService,
            final WebserviceInfo webserviceInfo,
            @Value("${sebserver.webservice.api.exam.time-suffix:3600000}") final Long examTimeSuffix) {

        this.examDAO = examDAO;
        this.applicationEventPublisher = applicationEventPublisher;
        this.sebRestrictionService = sebRestrictionService;
        this.lmsAPIService = lmsAPIService;
        this.updatePrefix = webserviceInfo.getLocalHostAddress()
                + "_" + webserviceInfo.getServerPort() + "_";
        this.examTimeSuffix = examTimeSuffix;
    }

    public SEBRestrictionService getSEBRestrictionService() {
        return this.sebRestrictionService;
    }

    String createUpdateId() {
        return this.updatePrefix + Utils.getMillisecondsNow();
    }

    Result<Set<String>> updateExamFromLMS(final Long lmsSetupId, final Map<String, Exam> exams) {

        return Result.tryCatch(() -> {
            final Set<String> failedOrMissing = new HashSet<>(exams.keySet());
            final String updateId = this.createUpdateId();

            // test overall LMS access
            try {
                this.lmsAPIService
                        .getLmsAPITemplate(lmsSetupId)
                        .getOrThrow()
                        .checkCourseAPIAccess();
            } catch (final Exception e) {
                log.warn("No LMS access, mark all exams of the LMS as not connected to LMS");
                if (!failedOrMissing.isEmpty()) {
                    failedOrMissing
                            .stream()
                            .forEach(quizId -> {
                                try {
                                    final Exam exam = exams.get(quizId);
                                    if (exam.lmsAvailable == null || exam.isLmsAvailable()) {
                                        this.examDAO.markLMSAvailability(quizId, false, updateId);
                                    }
                                } catch (final Exception ee) {
                                    log.error("Failed to mark exam: {} as not connected to LMS", quizId, ee);
                                }
                            });
                }
                return failedOrMissing;
            }

            this.lmsAPIService
                    .getLmsAPITemplate(lmsSetupId)
                    .map(template -> {
                        // TODO flush only involved courses from cache!
                        template.clearCourseCache();
                        return template;
                    })
                    .flatMap(template -> template.getQuizzes(new HashSet<>(exams.keySet())))
                    .onError(error -> log.warn(
                            "Failed to get quizzes from LMS Setup: {} cause: {}",
                            lmsSetupId,
                            error.getMessage()))
                    .getOr(Collections.emptyList())
                    .stream()
                    .forEach(quiz -> {

                        try {
                            final Exam exam = exams.get(quiz.id);
                            if (hasChanges(exam, quiz)) {

                                final Result<QuizData> updateQuizData = this.examDAO
                                        .updateQuizData(exam.id, quiz, updateId);

                                if (updateQuizData.hasError()) {
                                    log.error("Failed to update quiz data for exam: {}", quiz,
                                            updateQuizData.getError());
                                } else {
                                    if (!exam.isLmsAvailable()) {
                                        this.examDAO.markLMSAvailability(quiz.id, true, updateId);
                                    }
                                    failedOrMissing.remove(quiz.id);
                                    log.info("Updated quiz data for exam: {}", updateQuizData.get());
                                }

                            } else {
                                if (!exam.isLmsAvailable()) {
                                    this.examDAO.markLMSAvailability(quiz.id, true, updateId);
                                }
                                failedOrMissing.remove(quiz.id);
                            }
                        } catch (final Exception e) {
                            log.error("Unexpected error while trying to update quiz data for exam: {}", quiz, e);
                        }
                    });

            if (!failedOrMissing.isEmpty()) {
                new HashSet<>(failedOrMissing).stream()
                        .forEach(quizId -> tryRecoverQuizData(quizId, lmsSetupId, exams, updateId)
                                .onSuccess(quizData -> failedOrMissing.remove(quizId)));
            }

            return failedOrMissing;
        });
    }

    Result<Exam> updateRunning(final Long examId) {
        return this.examDAO.byPK(examId)
                .map(exam -> {
                    final DateTime now = DateTime.now(DateTimeZone.UTC);
                    if (exam.getStatus() == ExamStatus.UP_COMING
                            && exam.endTime.plus(this.examTimeSuffix).isBefore(now)) {
                        return setRunning(exam, this.createUpdateId())
                                .getOr(exam);
                    } else {
                        return exam;
                    }
                });
    }

    void updateState(
            final Exam exam,
            final DateTime now,
            final long leadTime,
            final long followupTime,
            final String updateId) {

        try {
            // Include leadTime and followupTime
            final DateTime startTimeThreshold = now.plus(leadTime);
            final DateTime endTimeThreshold = now.minus(leadTime);

            if (log.isDebugEnabled()) {
                log.debug("Check exam update for startTimeThreshold: {}, endTimeThreshold {}, exam: {}",
                        startTimeThreshold,
                        endTimeThreshold,
                        exam);
            }

            if (exam.status == ExamStatus.ARCHIVED) {
                log.warn("Exam in unexpected state for status update. Skip update. Exam: {}", exam);
                return;
            }

            if (exam.status != ExamStatus.RUNNING && withinTimeframe(
                    exam.startTime,
                    startTimeThreshold,
                    exam.endTime,
                    endTimeThreshold)) {

                if (withinTimeframe(exam.startTime, startTimeThreshold, exam.endTime, endTimeThreshold)) {
                    setRunning(exam, updateId)
                            .onError(error -> log.error("Failed to update exam to running state: {}",
                                    exam,
                                    error));
                    return;
                }
            }

            if (exam.status != ExamStatus.FINISHED &&
                    exam.endTime != null &&
                    endTimeThreshold.isAfter(exam.endTime)) {
                setFinished(exam, updateId)
                        .onError(error -> log.error("Failed to update exam to finished state: {}",
                                exam,
                                error));
                return;
            }

            if (exam.status != ExamStatus.UP_COMING &&
                    exam.startTime != null &&
                    startTimeThreshold.isBefore(exam.startTime)) {
                setUpcoming(exam, updateId)
                        .onError(error -> log.error("Failed to update exam to up-coming state: {}",
                                exam,
                                error));
            }
        } catch (final Exception e) {
            log.error("Unexpected error while trying to update exam state for exam: {}", exam, e);
        }
    }

    private boolean withinTimeframe(
            final DateTime startTime,
            final DateTime startTimeThreshold,
            final DateTime endTime,
            final DateTime endTimeThreshold) {

        if (startTime == null && endTime == null) {
            return true;
        }

        if (startTime == null && endTime.isAfter(endTimeThreshold)) {
            return true;
        }

        if (endTime == null && startTime.isBefore(startTimeThreshold)) {
            return true;
        }

        return (startTime.isBefore(startTimeThreshold) && endTime.isAfter(endTimeThreshold));
    }

    Result<Exam> setUpcoming(final Exam exam, final String updateId) {
        if (log.isDebugEnabled()) {
            log.debug("Update exam as up-coming: {}", exam);
        }

        return this.examDAO
                .placeLock(exam.id, updateId)
                .flatMap(e -> this.examDAO.updateState(exam.id, ExamStatus.UP_COMING, updateId))
                .map(e -> {
                    this.examDAO
                            .releaseLock(e, updateId)
                            .onError(error -> this.examDAO
                                    .forceUnlock(exam.id)
                                    .onError(unlockError -> log.error(
                                            "Failed to force unlock update look for exam: {}",
                                            exam.id)));
                    return e;
                })
                .map(e -> {
                    this.applicationEventPublisher.publishEvent(new ExamResetEvent(exam));
                    return exam;
                });
    }

    Result<Exam> setRunning(final Exam exam, final String updateId) {
        if (log.isDebugEnabled()) {
            log.debug("Update exam as running: {}", exam);
        }

        return this.examDAO
                .placeLock(exam.id, updateId)
                .flatMap(e -> this.examDAO.updateState(exam.id, ExamStatus.RUNNING, updateId))
                .map(e -> {
                    this.examDAO
                            .releaseLock(e, updateId)
                            .onError(error -> this.examDAO
                                    .forceUnlock(exam.id)
                                    .onError(unlockError -> log.error(
                                            "Failed to force unlock update look for exam: {}",
                                            exam.id)));
                    return e;
                })
                .map(e -> {
                    this.applicationEventPublisher.publishEvent(new ExamStartedEvent(exam));
                    return exam;
                });
    }

    Result<Exam> setFinished(final Exam exam, final String updateId) {
        if (log.isDebugEnabled()) {
            log.debug("Update exam as finished: {}", exam);
        }

        return this.examDAO
                .placeLock(exam.id, updateId)
                .flatMap(e -> this.examDAO.updateState(exam.id, ExamStatus.FINISHED, updateId))
                .map(e -> {
                    this.examDAO
                            .releaseLock(e, updateId)
                            .onError(error -> this.examDAO
                                    .forceUnlock(exam.id)
                                    .onError(unlockError -> log.error(
                                            "Failed to force unlock update look for exam: {}",
                                            exam.id)));
                    return e;
                })
                .map(e -> {
                    this.applicationEventPublisher.publishEvent(new ExamFinishedEvent(exam));
                    return exam;
                });
    }

    private boolean hasChanges(final Exam exam, final QuizData quizData) {
        if (!Objects.equals(exam.name, quizData.name) ||
                !Objects.equals(exam.startTime, quizData.startTime) ||
                !Objects.equals(exam.endTime, quizData.endTime) ||
                !Objects.equals(exam.getDescription(), quizData.description) ||
                !Objects.equals(exam.getStartURL(), quizData.startURL)) {

            return true;
        }

        if (quizData.additionalAttributes != null && !quizData.additionalAttributes.isEmpty()) {
            for (final Map.Entry<String, String> attr : quizData.additionalAttributes.entrySet()) {
                if (!Objects.equals(exam.getAdditionalAttribute(attr.getKey()), attr.getValue())) {
                    return true;
                }
            }
        }

        return false;
    }

    private Result<QuizData> tryRecoverQuizData(
            final String quizId,
            final Long lmsSetupId,
            final Map<String, Exam> exams,
            final String updateId) {

        return Result.tryCatch(() -> {
            final Exam exam = exams.get(quizId);
            final LmsAPITemplate lmsTemplate = this.lmsAPIService
                    .getLmsAPITemplate(lmsSetupId)
                    .getOrThrow();

            // If this is a Moodle quiz, try to recover from eventually restore of the quiz on the LMS side
            // NOTE: This is a workaround for Moodle quizzes that had have a recovery within the sandbox tool
            //       Where potentially quiz identifiers get changed during such a recovery and the SEB Server
            //       internal mapping is not working properly anymore. In this case we try to recover from such
            //       a case by using the short name of the quiz and search for the quiz within the course with this
            //       short name. If one quiz has been found that matches all criteria, we adapt the internal id
            //       mapping to this quiz.
            //       If recovering fails, this returns null and the calling side must handle the lack of quiz data
            if (lmsTemplate.getType() == LmsType.MOODLE) {

                log.info("Try to recover quiz data for Moodle quiz with internal identifier: {}", quizId);

                if (exam != null && exam.name != null
                        && !exam.name.startsWith(Constants.SQUARE_BRACE_OPEN.toString())) {

                    log.debug("Found formerName quiz name: {}", exam.name);

                    // get the course name identifier
                    final String shortname = MoodleCourseAccess.getShortname(quizId);
                    if (StringUtils.isNotBlank(shortname)) {

                        log.debug("Using short-name: {} for recovering", shortname);

                        final QuizData recoveredQuizData = lmsTemplate
                                .getQuizzes(new FilterMap())
                                .getOrThrow()
                                .stream()
                                .filter(quiz -> {
                                    final String qShortName = MoodleCourseAccess.getShortname(quiz.id);
                                    return qShortName != null && qShortName.equals(shortname);
                                })
                                .filter(quiz -> exam.name.equals(quiz.name))
                                .findAny()
                                .get();

                        if (recoveredQuizData != null) {

                            log.debug("Found quiz data for recovering: {}", recoveredQuizData);

                            // save exam with new external id and quit data
                            this.examDAO
                                    .updateQuizData(exam.id, recoveredQuizData, updateId)
                                    .getOrThrow();

                            log.debug("Successfully recovered exam quiz data to new externalId {}",
                                    recoveredQuizData.id);
                        }
                        return recoveredQuizData;
                    }
                }
            }

            if (exam.lmsAvailable == null || exam.isLmsAvailable()) {
                this.examDAO.markLMSAvailability(quizId, false, updateId);
            }
            throw new RuntimeException("Not Available");
        });
    }

}
