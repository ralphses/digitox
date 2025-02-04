package com.clicks.digitox.domain.sleep_info.entity;

import java.time.LocalDate;

public class SleepInfo {
    private long screenTime;
    private long sleepDuration;
    private int sleepQuality;
    private final String userEmail;
    private final LocalDate date;

    public SleepInfo(long screenTime, long sleepDuration, int sleepQuality, String userEmail, LocalDate date) {
        this.screenTime = screenTime;
        this.sleepDuration = sleepDuration;
        this.sleepQuality = sleepQuality;
        this.userEmail = userEmail;
        this.date = date;
    }


    public SleepInfo(String userEmail) {
        this.userEmail = userEmail;
        this.date = LocalDate.now();
        this.sleepDuration = 86400;
        this.sleepQuality = 100;
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

    public LocalDate getDate() {
        return date;
    }

    public long updateScreenTime(long screenTime) {
        this.screenTime = screenTime;
        this.sleepDuration -= screenTime;
        this.sleepQuality = calculateSleepQuality();
        return this.screenTime;
    }

    private int calculateSleepQuality() {
        if (sleepDuration >= 32400) {
            return 100;
        }
        return (int) ((double) sleepDuration / 32400 * 100);
    }

}
