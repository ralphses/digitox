package com.digitoxapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppUsageSessionDao {

    @Insert
    void insertSession(AppUsageSession session);

    @Query("SELECT * FROM sessions")
    List<AppUsageSession> getAllSessions();

    @Query("DELETE FROM sessions")
    void deleteAllSessions();
}
