package com.clicks.digitox.application.sleep_info.use_case;

import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.sleep_info.service.SleepInfoService;
import com.clicks.digitox.domain.sleep_info.utils.SleepInfoMapper;
import com.clicks.digitox.shared.annotations.UseCase;

@UseCase
public class GetTodaySleepInfo {

    private final SleepInfoService sleepInfoService;

    public GetTodaySleepInfo(SleepInfoService sleepInfoService) {
        this.sleepInfoService = sleepInfoService;
    }

    public SleepInfoDto execute(String userEmail) {
        return SleepInfoMapper.toSleepInfoDto(sleepInfoService.getTodayInfo(userEmail));
    }
}
