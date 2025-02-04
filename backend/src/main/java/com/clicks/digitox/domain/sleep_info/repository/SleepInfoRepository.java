package com.clicks.digitox.domain.sleep_info.repository;

import com.clicks.digitox.domain.sleep_info.entity.SleepInfo;

public interface SleepInfoRepository {
    SleepInfo save(SleepInfo sleepInfo);

    SleepInfo findByEmail(String userEmail);

    SleepInfo findTodayInfo(String userEmail);

    void update(SleepInfo sleepInfo);
}
