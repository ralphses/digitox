package com.clicks.digitox.application.user.use_case;

import com.clicks.digitox.domain.milestone.DailyMilestoneDto;
import com.clicks.digitox.domain.milestone.DailyMilestoneService;
import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.sleep_info.service.SleepInfoService;
import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.dto.UserProfileDto;
import com.clicks.digitox.domain.user.service.UserService;
import com.clicks.digitox.shared.annotations.UseCase;

import java.util.List;

@UseCase
public class GetUserProfile {

    private final UserService userService;
    private final SleepInfoService sleepInfoService;
    private final DailyMilestoneService dailyMilestoneService;

    public GetUserProfile(UserService userService, SleepInfoService sleepInfoService, DailyMilestoneService dailyMilestoneService) {
        this.userService = userService;
        this.sleepInfoService = sleepInfoService;
        this.dailyMilestoneService = dailyMilestoneService;
    }

    public UserProfileDto execute(String email) {
        UserDto user = userService.findUserByEmail(email);
        SleepInfoDto sleepInfo = sleepInfoService.getForUser(email);
        List<DailyMilestoneDto> userMilestones = dailyMilestoneService.getByUserEmail(email);
        return new UserProfileDto(user, sleepInfo, userMilestones);
    }
}
