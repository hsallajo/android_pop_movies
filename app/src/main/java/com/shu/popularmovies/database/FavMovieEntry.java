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
    @ColumnInfo(name = "movie_popularity")
    private float popularity;
    @ColumnInfo(name = "movie_video")
    private int video;
    @ColumnInfo(name = "movie_vote_count")
    private int voteCount;
    @ColumnInfo(name = "movie_vote_average")
    private float voteAverage;
    @ColumnInfo(name = "movie_original_language")
    private String originalLanguage;
    @ColumnInfo(name = "movie_original_title")
    private String originalTitle;
    @ColumnInfo(name = "movie_genre_ids")
    private String genreIds;
    @ColumnInfo(name = "movie_backdrop_path")
    private String backdropPath;
    @ColumnInfo(name = "movie_adult")
    private int adult;
    @ColumnInfo(name = "movie_overview")
    private String overview;
    @ColumnInfo(name = "movie_release_date")
    private String releaseDate;

    @Ignore
    public FavMovieEntry(String title, int movieId, String posterPath
            , float popularity, int video, int voteCount
            , float voteAverage, String originalLanguage, String originalTitle
            , String genreIds, String backdropPath, int adult
            , String overview, String releaseDate) {
        this.title = title;
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.video = video;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;

    }

    public FavMovieEntry(int id, String title, int movieId, String posterPath
            , float popularity, int video, int voteCount
            , float voteAverage, String originalLanguage, String originalTitle
            , String genreIds, String backdropPath, int adult
            , String overview, String releaseDate) {
        this.id = id;
        this.title = title;
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.video = video;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
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

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(String genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
