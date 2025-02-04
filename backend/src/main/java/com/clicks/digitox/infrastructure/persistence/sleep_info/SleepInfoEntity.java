package com.clicks.digitox.infrastructure.persistence.sleep_info;

import com.clicks.digitox.domain.sleep_info.entity.SleepInfo;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class SleepInfoEntity {


    @EmbeddedId
    private SleepInfoEntityId id;
    private String userEmail;
    private long screenTime;
    private long sleepDuration;
    private int sleepQuality;
    private LocalDate createdAt;

    public SleepInfoEntity(String userEmail, long screenTime, long sleepDuration, int sleepQuality, LocalDate createdAt) {
        this.userEmail = userEmail;
        this.screenTime = screenTime;
        this.sleepDuration = sleepDuration;
        this.sleepQuality = sleepQuality;
        this.createdAt = createdAt;
        this.id = new SleepInfoEntityId();
    }

    protected SleepInfoEntity() {}

    public SleepInfoEntityId getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public long getScreenTime() {
        return screenTime;
    }

    public long getSleepDuration() {
        return sleepDuration;
    }

    public int getSleepQuality() {
        return sleepQuality;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void update(SleepInfo sleepInfo) {
        this.screenTime = sleepInfo.getScreenTime();
        this.sleepDuration = sleepInfo.getSleepDuration();
        this.sleepQuality = sleepInfo.getSleepQuality();
    }
}
