package com.filbertfilbert.uts.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.filbertfilbert.uts.model.Wahana;

import java.util.List;

@Dao
public interface WahanaDao {

    @Query("SELECT * FROM wahana")
    List<Wahana> getAll();

    @Insert
    void insert(Wahana wahana);

    @Update
    void update(Wahana wahana);

    @Delete
    void delete (Wahana wahana);
}