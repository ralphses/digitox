package com.clicks.digitox.domain.milestone;

public class MilestoneMapper {
    private MilestoneMapper(){}

    public static DailyMilestoneDto milestoneDto(DailyMilestone milestone){
        return new DailyMilestoneDto(
                milestone.getId().toString(),
                milestone.getUserEmail(),
                milestone.getLabel(),
                milestone.getProgress(),
                milestone.isCompleted(),
                milestone.getMaxScreenTime(),
                milestone.getDate().toString(),
                milestone.getType().name()
        );
    }
}
