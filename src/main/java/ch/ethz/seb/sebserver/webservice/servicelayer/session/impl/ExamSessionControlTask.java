/*
 * Copyright (c) 2019 ETH Zürich, Educational Development and Technology (LET)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ch.ethz.seb.sebserver.webservice.servicelayer.session.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ch.ethz.seb.sebserver.SEBServerInit;
import ch.ethz.seb.sebserver.SEBServerInitEvent;
import ch.ethz.seb.sebserver.gbl.model.exam.Exam;
import ch.ethz.seb.sebserver.gbl.profile.WebServiceProfile;
import ch.ethz.seb.sebserver.gbl.util.Result;
import ch.ethz.seb.sebserver.webservice.WebserviceInfo;
import ch.ethz.seb.sebserver.webservice.servicelayer.dao.ExamDAO;
import ch.ethz.seb.sebserver.webservice.servicelayer.session.ExamProctoringRoomService;
import ch.ethz.seb.sebserver.webservice.servicelayer.session.SEBClientConnectionService;

@Service
@WebServiceProfile
public class ExamSessionControlTask implements DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(ExamSessionControlTask.class);

    private final ExamDAO examDAO;
    private final SEBClientConnectionService sebClientConnectionService;
    private final ExamUpdateHandler examUpdateHandler;
    private final ExamProctoringRoomService examProcotringRoomService;
    private final WebserviceInfo webserviceInfo;

    private final Long examTimePrefix;
    private final Long examTimeSuffix;
    private final String examTaskCron;
    private final long pingUpdateRate;

    protected ExamSessionControlTask(
            final ExamDAO examDAO,
            final SEBClientConnectionService sebClientConnectionService,
            final ExamUpdateHandler examUpdateHandler,
            final ExamProctoringRoomService examProcotringRoomService,
            final WebserviceInfo webserviceInfo,
            @Value("${sebserver.webservice.api.exam.time-prefix:3600000}") final Long examTimePrefix,
            @Value("${sebserver.webservice.api.exam.time-suffix:3600000}") final Long examTimeSuffix,
            @Value("${sebserver.webservice.api.exam.update-interval:1 * * * * *}") final String examTaskCron,
            @Value("${sebserver.webservice.api.exam.update-ping:5000}") final Long pingUpdateRate) {

        this.examDAO = examDAO;
        this.sebClientConnectionService = sebClientConnectionService;
        this.examUpdateHandler = examUpdateHandler;
        this.webserviceInfo = webserviceInfo;
        this.examTimePrefix = examTimePrefix;
        this.examTimeSuffix = examTimeSuffix;
        this.examTaskCron = examTaskCron;
        this.pingUpdateRate = pingUpdateRate;
        this.examProcotringRoomService = examProcotringRoomService;
    }

    @EventListener(SEBServerInitEvent.class)
    private void init() {
        SEBServerInit.INIT_LOGGER.info("------>");
        SEBServerInit.INIT_LOGGER.info("------> Activate exam run controller background task");
        SEBServerInit.INIT_LOGGER.info("--------> Task runs on an cron-job interval of {}", this.examTaskCron);
        SEBServerInit.INIT_LOGGER.info(
                "--------> Real exam running time span is expanded on {} before start and {} milliseconds after ending",
                this.examTimePrefix,
                this.examTimeSuffix);

        this.updateMaster();

        SEBServerInit.INIT_LOGGER.info("------>");
        SEBServerInit.INIT_LOGGER.info(
                "------> Activate SEB lost-ping-event update background task on a fix rate of: {} milliseconds",
                this.pingUpdateRate);
    }

    @Scheduled(
            fixedDelayString = "${sebserver.webservice.api.exam.update-interval:60000}",
            initialDelay = 10000)
    private void examRunUpdateTask() {

        if (!this.webserviceInfo.isMaster()) {
            return;
        }

        final String updateId = this.examUpdateHandler.createUpdateId();

        if (log.isDebugEnabled()) {
            log.debug("Run exam update task with Id: {}", updateId);
        }

        controlExamLMSUpdate();
        controlExamState(updateId);
        this.examDAO.releaseAgedLocks();
    }

    @Scheduled(
            fixedDelayString = "${sebserver.webservice.api.seb.lostping.update:5000}",
            initialDelay = 5000)
    private void examSessionUpdateTask() {

        updateMaster();

        if (!this.webserviceInfo.isMaster()) {
            return;
        }

        this.sebClientConnectionService.updatePingEvents();

        if (log.isTraceEnabled()) {
            log.trace("Run exam session update task");
        }

        this.sebClientConnectionService.cleanupInstructions();
        this.examProcotringRoomService.updateProctoringCollectingRooms();
    }

    @Scheduled(
            fixedRateString = "${sebserver.webservice.api.exam.session-cleanup:30000}",
            initialDelay = 30000)
    private void examSessionCleanupTask() {

        if (!this.webserviceInfo.isMaster()) {
            return;
        }

        if (log.isTraceEnabled()) {
            log.trace("Run exam session cleanup task");
        }

        this.sebClientConnectionService.cleanupInstructions();
    }

    private void controlExamLMSUpdate() {
        if (log.isTraceEnabled()) {
            log.trace("Start update exams from LMS");
        }

        try {

            // create mapping
            final Map<Long, Map<String, Exam>> examToLMSMapping = new HashMap<>();
            this.examDAO.allForLMSUpdate()
                    .onError(error -> log.error("Failed to update exams from LMS: ", error))
                    .getOr(Collections.emptyList())
                    .stream()
                    .forEach(exam -> {
                        final Map<String, Exam> examMap = (examToLMSMapping.computeIfAbsent(
                                exam.lmsSetupId,
                                lmsId -> new HashMap<>()));
                        examMap.put(exam.externalId, exam);
                    });

            // update per LMS Setup
            examToLMSMapping.entrySet()
                    .stream()
                    .forEach(updateEntry -> {
                        final Result<Set<String>> updateExamFromLMS = this.examUpdateHandler
                                .updateExamFromLMS(updateEntry.getKey(), updateEntry.getValue());

                        if (updateExamFromLMS.hasError()) {
                            log.error("Failed to update exams from LMS: ", updateExamFromLMS.getError());
                        } else {
                            final Set<String> failedExams = updateExamFromLMS.get();
                            if (!failedExams.isEmpty()) {
                                log.warn("Failed to update following exams from LMS: {}", failedExams);
                            }
                        }
                    });

        } catch (final Exception e) {
            log.error("Unexpected error while update exams from LMS: ", e);
        }
    }

    private void controlExamState(final String updateId) {
        if (log.isTraceEnabled()) {
            log.trace("Check starting exams: {}", updateId);
        }

        try {

            final DateTime now = DateTime.now(DateTimeZone.UTC);
            this.examDAO
                    .allThatNeedsStatusUpdate(this.examTimePrefix, this.examTimeSuffix)
                    .getOrThrow()
                    .stream()
                    .forEach(exam -> this.examUpdateHandler.updateState(
                            exam,
                            now,
                            this.examTimePrefix,
                            this.examTimeSuffix,
                            updateId));

        } catch (final Exception e) {
            log.error("Unexpected error while trying to run exam state update task: ", e);
        }
    }

    private void updateMaster() {
        this.webserviceInfo.updateMaster();
    }

    @Override
    public void destroy() {
        // TODO try to reset master
    }

}
