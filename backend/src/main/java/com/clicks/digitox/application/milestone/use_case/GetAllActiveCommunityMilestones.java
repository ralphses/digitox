package com.clicks.digitox.application.milestone.use_case;

import com.clicks.digitox.domain.milestone.DailyMilestoneDto;
import com.clicks.digitox.domain.milestone.DailyMilestoneService;
import com.clicks.digitox.shared.annotations.UseCase;

import java.util.List;

@UseCase
public class GetAllActiveCommunityMilestones {

    private final DailyMilestoneService dailyMilestoneService;

    public GetAllActiveCommunityMilestones(DailyMilestoneService dailyMilestoneService) {
        this.dailyMilestoneService = dailyMilestoneService;
    }

    public List<DailyMilestoneDto> execute() {
        return dailyMilestoneService.getCommunityMilestones();
    }
}
