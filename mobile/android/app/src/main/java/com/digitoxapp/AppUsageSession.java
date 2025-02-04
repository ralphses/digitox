package com.digitoxapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sessions")
public class AppUsageSession {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String appName;
    private long startTime;
    private long endTime;
    private long totalDuration;

    // Constructor
    public AppUsageSession(String appName, long startTime, long endTime, long totalDuration) {
        this.appName = appName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalDuration = totalDuration;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }
}
