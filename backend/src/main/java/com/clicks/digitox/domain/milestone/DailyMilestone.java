package com.clicks.digitox.domain.milestone;

import java.time.LocalDate;
import java.util.UUID;

public class DailyMilestone {
    private final UUID id;
    private final String userEmail;
    private final String label;
    private final LocalDate date;
    private int progress;
    private boolean completed;
    private final long maxScreenTime;
    private final MilestoneType type;

    public DailyMilestone(String userEmail, String label, long maxScreenTime, LocalDate date, MilestoneType type) {
        this.label = label;
        this.maxScreenTime = maxScreenTime;
        this.userEmail = userEmail;
        this.date = date;
        this.type = type;
        this.id = UUID.randomUUID();
    }

    public DailyMilestone(UUID id, String userEmail, String label, LocalDate date, int progress, boolean completed, long maxScreenTime, MilestoneType type) {
        this.id = id;
        this.userEmail = userEmail;
        this.label = label;
        this.date = date;
        this.progress = progress;
        this.completed = completed;
        this.maxScreenTime = maxScreenTime;
        this.type = type;
    }

    public MilestoneType getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public long getMaxScreenTime() {
        return maxScreenTime;
    }

    public String getLabel() {
        return label;
    }

    public int getProgress() {
        return progress;
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

    public UUID getId() {
        return id;
    }
}
