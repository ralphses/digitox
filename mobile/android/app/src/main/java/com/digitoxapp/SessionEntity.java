package com.digitoxapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SessionEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long startTime;
    private long endTime;

    public SessionEntity(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getDuration() {
        return endTime - startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
