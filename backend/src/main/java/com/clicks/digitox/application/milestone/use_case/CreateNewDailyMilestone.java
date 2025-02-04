package com.clicks.digitox.application.milestone.use_case;

import com.clicks.digitox.domain.milestone.DailyMilestoneDto;
import com.clicks.digitox.domain.milestone.DailyMilestoneService;
import com.clicks.digitox.domain.milestone.MilestoneType;
import com.clicks.digitox.domain.user.exceptions.UserNotFoundException;
import com.clicks.digitox.domain.user.service.UserService;
import com.clicks.digitox.shared.annotations.UseCase;

import java.time.LocalDate;

@UseCase
public class CreateNewDailyMilestone {

    private final DailyMilestoneService dailyMilestoneService;
    private final UserService userService;

    public CreateNewDailyMilestone(DailyMilestoneService dailyMilestoneService, UserService userService) {
        this.dailyMilestoneService = dailyMilestoneService;
        this.userService = userService;
    }

    public DailyMilestoneDto execute(String userEmail, String label, long maxScreenTime, LocalDate date, MilestoneType type) {
        if (!userService.exists(userEmail)) {
           throw new UserNotFoundException();
        }
        return dailyMilestoneService.create(
                userEmail,
                label,
                maxScreenTime,
                date,
                type
        );
    }
}
