package com.clicks.digitox.infrastructure.persistence.sleep_info;

import com.clicks.digitox.domain.sleep_info.entity.SleepInfo;

public class SleepInfoEntityMapper {

    private SleepInfoEntityMapper() {}

    public static SleepInfoEntity toSleepInfoEntity(SleepInfo sleepInfo) {
        return new SleepInfoEntity(
                sleepInfo.getUserEmail(),
                sleepInfo.getScreenTime(),
                sleepInfo.getSleepDuration(),
                sleepInfo.getSleepQuality(),
                sleepInfo.getDate()
        );
    }

    public static SleepInfo toSleepInfo(SleepInfoEntity sleepInfoEntity) {
        return new SleepInfo(
                sleepInfoEntity.getScreenTime(),
                sleepInfoEntity.getSleepDuration(),
                sleepInfoEntity.getSleepQuality(),
                sleepInfoEntity.getUserEmail(),
                sleepInfoEntity.getCreatedAt()
        );
    }
}
