package com.filbertfilbert.uts;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Wahana.class,},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WahanaDao wahanaDao();
}
