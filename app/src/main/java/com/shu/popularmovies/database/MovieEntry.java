package com.shu.popularmovies.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    @ColumnInfo(name = "movie_id")
    private int movieId;

    @Ignore
    public MovieEntry(String title, int movieId){
        this.title = title;
        this.movieId = movieId;
    }

    public MovieEntry(int id, String title, int movieId){
        this.id = id;
        this.title = title;
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
