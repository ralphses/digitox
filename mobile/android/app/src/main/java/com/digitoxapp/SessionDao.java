package com.digitoxapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SessionDao {
    @Insert
    void insert(SessionEntity session);

    @Query("SELECT * FROM SessionEntity")
    List<SessionEntity> getAllSessions();
}
