package com.clicks.digitox.infrastructure.persistence.milestone;

import com.clicks.digitox.domain.milestone.MilestoneType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

@Entity
public class DailyMilestoneEntity {

    @EmbeddedId
    private final DailyMilestoneId dailyMilestoneId;

    private String userEmail;
    private String label;
    private LocalDate date;
    private int progress;
    private boolean completed;
    private long maxScreenTime;

    @Enumerated(EnumType.STRING)
    private MilestoneType type;

    protected  DailyMilestoneEntity(){
        this.dailyMilestoneId = new DailyMilestoneId();
    }

    public DailyMilestoneEntity(String userEmail, String label, LocalDate date, long maxScreenTime, MilestoneType type){
        this.userEmail = userEmail;
        this.label = label;
        this.date = date;
        this.maxScreenTime = maxScreenTime;
        this.type = type;
        this.dailyMilestoneId = new DailyMilestoneId();
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setMaxScreenTime(long maxScreenTime) {
        this.maxScreenTime = maxScreenTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public DailyMilestoneId getDailyMilestoneId() {
        return dailyMilestoneId;
    }

    public String getLabel() {
        return label;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getProgress() {
        return progress;
    }

    public long getMaxScreenTime() {
        return maxScreenTime;
    }

    public MilestoneType getType() {
        return type;
    }
}
