package com.clicks.digitox.application.sleep_info.use_case;

import com.clicks.digitox.domain.sleep_info.entity.SleepInfo;
import com.clicks.digitox.domain.sleep_info.service.SleepInfoService;
import com.clicks.digitox.domain.sleep_info.utils.SleepInfoMapper;
import com.clicks.digitox.domain.user.service.UserService;
import com.clicks.digitox.shared.annotations.UseCase;


@UseCase
public class AddNewScreenTime {

    private final SleepInfoService sleepInfoService;
    private final UserService userService;

    public AddNewScreenTime(SleepInfoService sleepInfoService, UserService userService) {
        this.sleepInfoService = sleepInfoService;
        this.userService = userService;
    }

    public String execute(long currentScreenTime, String userEmail) {
        SleepInfo todayInfo = sleepInfoService.getTodayInfo(userEmail);
        userService.updateTotalScreenTime(userEmail, currentScreenTime);
        return SleepInfoMapper.formatTime(sleepInfoService.updateForUser(todayInfo, currentScreenTime));
    }
}
