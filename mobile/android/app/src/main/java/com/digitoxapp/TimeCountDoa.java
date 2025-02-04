package com.digitoxapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TimeCountDoa {

    @Insert
    void insert(TimeCount timeCount);

    @Query("SELECT * FROM time_count WHERE date = :date LIMIT 1")
    TimeCount getByDate(String date);

    @Update
    void update(TimeCount existingRecord);
}
