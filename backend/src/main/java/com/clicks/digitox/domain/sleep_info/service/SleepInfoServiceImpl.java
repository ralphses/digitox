package com.clicks.digitox.domain.sleep_info.service;

import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.sleep_info.entity.SleepInfo;
import com.clicks.digitox.domain.sleep_info.repository.SleepInfoRepository;
import com.clicks.digitox.domain.sleep_info.utils.SleepInfoMapper;
import com.clicks.digitox.shared.annotations.ApplicationService;


@ApplicationService
public class SleepInfoServiceImpl implements SleepInfoService {
    private final SleepInfoRepository sleepInfoRepository;

    public SleepInfoServiceImpl(SleepInfoRepository sleepInfoRepository) {
        this.sleepInfoRepository = sleepInfoRepository;
    }

    @Override
    public SleepInfoDto createForUser(String email) {
        SleepInfo newSleepInfo = new SleepInfo(email);
        SleepInfo saved = sleepInfoRepository.save(newSleepInfo);
        return SleepInfoMapper.toSleepInfoDto(saved);
    }

    @Override
    public SleepInfoDto getForUser(String userEmail) {
        return SleepInfoMapper.toSleepInfoDto(sleepInfoRepository.findByEmail(userEmail));
    }

    @Override
    public long updateForUser(SleepInfo sleepInfo, long activeScreenTime) {
        long updateScreenTime = sleepInfo.updateScreenTime(activeScreenTime);
        sleepInfoRepository.update(sleepInfo);
        return updateScreenTime;
    }

    @Override
    public SleepInfo getTodayInfo(String userEmail) {
        return sleepInfoRepository.findTodayInfo(userEmail);
    }
}
