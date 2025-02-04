package com.clicks.digitox.application.milestone.use_case;

import com.clicks.digitox.domain.milestone.DailyMilestoneServiceImpl;
import com.clicks.digitox.domain.milestone.DailyMilestoneDto;
import com.clicks.digitox.shared.annotations.UseCase;

import java.util.List;

@UseCase
public class GetUserDailyMileStones {

    private final DailyMilestoneServiceImpl dailyMilestoneServiceImpl;

    public GetUserDailyMileStones(DailyMilestoneServiceImpl dailyMilestoneServiceImpl) {
        this.dailyMilestoneServiceImpl = dailyMilestoneServiceImpl;
    }

    public List<DailyMilestoneDto> execute(String userEmail) {
        return dailyMilestoneServiceImpl.getByUserEmail(userEmail);
    }
}
