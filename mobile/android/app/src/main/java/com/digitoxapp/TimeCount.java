package com.digitoxapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "time_count")
public class TimeCount {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date; // Stored as a string in the database
    private long duration; // Duration in seconds or milliseconds

    public TimeCount(String date, long duration) {
        this.date = date;
        this.duration = duration;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
