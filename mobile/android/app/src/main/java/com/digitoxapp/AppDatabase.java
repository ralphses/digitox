package com.digitoxapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SessionEntity.class, AppUsageSession.class, TimeCount.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SessionDao sessionDao();
    public abstract AppUsageSessionDao appUsageSessionDao();
    public abstract TimeCountDoa timeCountDoa();
}
