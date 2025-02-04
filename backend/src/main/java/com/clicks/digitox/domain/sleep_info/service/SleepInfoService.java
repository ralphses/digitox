package com.clicks.digitox.domain.sleep_info.service;

import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.sleep_info.entity.SleepInfo;

public interface SleepInfoService {
    SleepInfoDto createForUser(String email);

    SleepInfoDto getForUser(String userEmail);

    long updateForUser(SleepInfo sleepInfo, long activeScreenTime);

    SleepInfo getTodayInfo(String userEmail);
}
