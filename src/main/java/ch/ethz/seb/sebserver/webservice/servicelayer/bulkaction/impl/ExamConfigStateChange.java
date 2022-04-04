/*
 * Copyright (c) 2022 ETH Zürich, Educational Development and Technology (LET)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ch.ethz.seb.sebserver.webservice.servicelayer.bulkaction.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import ch.ethz.seb.sebserver.gbl.api.API.BatchActionType;
import ch.ethz.seb.sebserver.gbl.api.APIMessage;
import ch.ethz.seb.sebserver.gbl.model.BatchAction;
import ch.ethz.seb.sebserver.gbl.model.EntityKey;
import ch.ethz.seb.sebserver.gbl.model.sebconfig.ConfigurationNode.ConfigurationStatus;
import ch.ethz.seb.sebserver.gbl.profile.WebServiceProfile;
import ch.ethz.seb.sebserver.gbl.util.Result;
import ch.ethz.seb.sebserver.webservice.servicelayer.bulkaction.BatchActionExec;
import io.micrometer.core.instrument.util.StringUtils;

@Component
@WebServiceProfile
public class ExamConfigStateChange implements BatchActionExec {

    @Override
    public BatchActionType actionType() {
        return BatchActionType.EXAM_CONFIG_STATE_CHANGE;
    }

    @Override
    public APIMessage checkConsistency(final Map<String, String> actionAttributes) {
        final ConfigurationStatus targetState = getTargetState(actionAttributes);
        if (targetState == null) {
            APIMessage.ErrorMessage.ILLEGAL_API_ARGUMENT
                    .of("Missing target state attribute for EXAM_CONFIG_STATE_CHANGE batch action");
        }
        return null;
    }

    @Override
    public Result<EntityKey> doSingleAction(final String modelId, final Map<String, String> actionAttributes) {
        return Result.tryCatch(() -> {

            final ConfigurationStatus targetState = getTargetState(actionAttributes);

            return new EntityKey(modelId, actionType().entityType);
        });
    }

    private ConfigurationStatus getTargetState(final Map<String, String> actionAttributes) {
        try {
            final String targetStateString = actionAttributes.get(BatchAction.ACTION_ATTRIBUT_TARGET_STATE);
            if (StringUtils.isBlank(targetStateString)) {
                return null;
            }

            return ConfigurationStatus.valueOf(targetStateString);
        } catch (final Exception e) {
            return null;
        }
    }

}
