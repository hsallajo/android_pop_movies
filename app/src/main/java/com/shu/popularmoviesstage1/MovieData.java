package com.shu.popularmoviesstage1;

import org.parceler.Parcel;

import java.util.List;

@Parcel
class MovieData {

    // members
    int id;
    String title;
    int vote_count;
    boolean video;
    double vote_average;
    double popularity;
    String poster_path;
    String original_language;
    String original_title;
    List<Integer> genre_ids;
    String backdrop_path;
    boolean adult;
    String overview;
    String release_date;

    public MovieData() {
    }

    public MovieData(int id
            , String title
            , int vote_count
            , boolean video
            , double vote_average
            , double popularity
            , String poster_path
            , String original_language
            , String original_title
            , List<Integer> genre_ids
            , String backdrop_path
            , boolean adult
            , String overview
            , String release_date
    ) {
        this.id = id;
        this.title = title;
        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.genre_ids = genre_ids;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;

    }
}
