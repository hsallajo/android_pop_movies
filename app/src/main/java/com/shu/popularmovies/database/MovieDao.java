package com.shu.popularmovies.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    List<MovieEntry> loadAllMovies();

    @Insert
    void insert(MovieEntry movie);

    @Delete
    void delete(MovieEntry movieEntry);
}
