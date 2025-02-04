package com.clicks.digitox.application.milestone.use_case;

import com.clicks.digitox.domain.milestone.DailyMilestoneService;
import com.clicks.digitox.infrastructure.persistence.milestone.DailyMilestoneId;
import com.clicks.digitox.shared.annotations.UseCase;

@UseCase
public class DeleteMileStone {

    private final DailyMilestoneService dailyMilestoneService;

    public DeleteMileStone(DailyMilestoneService dailyMilestoneService) {
        this.dailyMilestoneService = dailyMilestoneService;
    }

    public void execute(DailyMilestoneId dailyMilestoneId) {
        dailyMilestoneService.delete(dailyMilestoneId);
    }
}
