package com.shu.popularmoviesstage1;

import org.parceler.Parcel;
import java.util.List;

/**
 * Created by S. Huoponen as part of Udacity Nanodegree's
 * project 'Popular Movies' (2018).
 */

@Parcel
class MovieData {

    // members
    int id;
    String title;
    int voteCount;
    boolean video;
    double voteAverage;
    double popularity;
    String posterPath;
    String originalLanguage;
    String originalTitle;
    List<Integer> genreIds;
    String backdropPath;
    boolean adult;
    String overview;
    String releaseDate;

    public MovieData() {
    }

    public MovieData(int id
            , String title
            , int voteCount
            , boolean video
            , double voteAverage
            , double popularity
            , String posterPath
            , String originalLanguage
            , String originalTitle
            , List<Integer> genreIds
            , String backdropPath
            , boolean adult
            , String overview
            , String releaseDate
    ) {
        this.id = id;
        this.title = title;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;

    }
}
