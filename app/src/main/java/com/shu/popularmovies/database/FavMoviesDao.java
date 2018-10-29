package com.shu.popularmovies.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FavMoviesDao {

    @Query("SELECT * FROM movie")
    LiveData<List<FavMovieEntry>> loadAllMovies();

    @Query("SELECT * FROM movie")
    List<FavMovieEntry> loadAll();

    @Insert
    void insert(FavMovieEntry movie);

    @Update(onConflict= OnConflictStrategy.REPLACE)
    void updateMovie(FavMovieEntry movieEntry);

    @Query("DELETE FROM movie WHERE movie_id = :movieId")
    void deleteByMovieId(long movieId);
}
