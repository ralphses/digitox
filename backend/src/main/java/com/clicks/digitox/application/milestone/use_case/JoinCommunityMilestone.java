package com.clicks.digitox.application.milestone.use_case;

import com.clicks.digitox.domain.milestone.DailyMilestoneService;
import com.clicks.digitox.domain.user.service.UserService;
import com.clicks.digitox.shared.annotations.UseCase;

@UseCase
public class JoinCommunityMilestone {

    private final DailyMilestoneService dailyMilestoneService;
    private final UserService userService;

    public JoinCommunityMilestone(DailyMilestoneService dailyMilestoneService, UserService userService) {
        this.dailyMilestoneService = dailyMilestoneService;
        this.userService = userService;
    }

    public boolean execute(String userEmail, String milestoneId) {
        if(!dailyMilestoneService.existsById(milestoneId)){
            return false;
        }
        return userService.addMilestoneToUser(milestoneId, userEmail);
    }
}
