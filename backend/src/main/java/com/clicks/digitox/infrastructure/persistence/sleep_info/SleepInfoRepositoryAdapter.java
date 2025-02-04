package com.clicks.digitox.infrastructure.persistence.sleep_info;

import com.clicks.digitox.domain.sleep_info.entity.SleepInfo;
import com.clicks.digitox.domain.sleep_info.exceptions.SleepInfoExceptionNotFound;
import com.clicks.digitox.domain.sleep_info.repository.SleepInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SleepInfoRepositoryAdapter implements SleepInfoRepository {

    private final JpaSleepInfoRepository sleepInfoRepository;

    public SleepInfoRepositoryAdapter(JpaSleepInfoRepository sleepInfoRepository) {
        this.sleepInfoRepository = sleepInfoRepository;
    }

    @Override
    public SleepInfo save(SleepInfo sleepInfo) {
        sleepInfoRepository.save(SleepInfoEntityMapper.toSleepInfoEntity(sleepInfo));
        return sleepInfo;
    }

    @Override
    public SleepInfo findByEmail(String userEmail) {
        return sleepInfoRepository.findByUserEmail(userEmail)
                .map(SleepInfoEntityMapper::toSleepInfo )
                .orElseThrow(SleepInfoExceptionNotFound::new);
    }

    @Override
    public SleepInfo findTodayInfo(String userEmail) {
        SleepInfoEntity sleepInfoEntity = getSleepInfoEntity(userEmail);
        return SleepInfoEntityMapper.toSleepInfo(sleepInfoEntity);
    }

    private SleepInfoEntity getSleepInfoEntity(String userEmail) {
        return sleepInfoRepository.findByCreatedAtAndUserEmail(LocalDate.now(), userEmail)
                .orElseThrow(SleepInfoExceptionNotFound::new);
    }

    @Override
    @Transactional
    public void update(SleepInfo sleepInfo) {
        SleepInfoEntity sleepInfoEntity = getSleepInfoEntity(sleepInfo.getUserEmail());
        sleepInfoEntity.update(sleepInfo);
    }
}
