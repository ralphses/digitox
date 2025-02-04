package com.clicks.digitox.application.user.use_case;

import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.sleep_info.service.SleepInfoService;
import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.dto.UserProfileDto;
import com.clicks.digitox.domain.user.service.UserService;
import com.clicks.digitox.shared.annotations.UseCase;

@UseCase
public class GetUserProfile {

    private final UserService userService;
    private final SleepInfoService sleepInfoService;

    public GetUserProfile(UserService userService, SleepInfoService sleepInfoService) {
        this.userService = userService;
        this.sleepInfoService = sleepInfoService;
    }

    public UserProfileDto execute(String email) {
        UserDto user = userService.findUserByEmail(email);
        SleepInfoDto sleepInfo = sleepInfoService.getForUser(email);
        return new UserProfileDto(user, sleepInfo);
    }
}
