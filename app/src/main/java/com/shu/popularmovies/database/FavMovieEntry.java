package com.shu.popularmovies.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class FavMovieEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    @ColumnInfo(name = "movie_id")
    private int movieId;
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @Ignore
    public FavMovieEntry(String title, int movieId, String posterPath){
        this.title = title;
        this.movieId = movieId;
        this.posterPath = posterPath;
    }

    public FavMovieEntry(int id, String title, int movieId, String posterPath){
        this.id = id;
        this.title = title;
        this.movieId = movieId;
        this.posterPath = posterPath;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
