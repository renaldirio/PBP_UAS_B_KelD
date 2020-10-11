package com.filbertfilbert.uts.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.filbertfilbert.uts.database.WahanaDao;
import com.filbertfilbert.uts.model.Wahana;

@Database(entities = {Wahana.class,},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WahanaDao wahanaDao();
}
