package com.clicks.digitox.infrastructure.persistence.milestone;

import com.clicks.digitox.domain.milestone.DailyMilestone;

public class JpaMilestoneMapper {

    private JpaMilestoneMapper() {}

    public static DailyMilestone toDailyMilestone(DailyMilestoneEntity entity) {
        return new DailyMilestone(
                entity.getDailyMilestoneId().id(),
                entity.getUserEmail(),
                entity.getLabel(),
                entity.getDate(),
                entity.getProgress(),
                entity.isCompleted(),
                entity.getMaxScreenTime(),
                entity.getType()
        );
    }

    public static DailyMilestoneEntity toDailyMilestoneEntity(DailyMilestone milestone) {

        return new DailyMilestoneEntity(
                milestone.getUserEmail(),
                milestone.getLabel(),
                milestone.getDate(),
                milestone.getMaxScreenTime(),
                milestone.getType()
        );
    }
}
