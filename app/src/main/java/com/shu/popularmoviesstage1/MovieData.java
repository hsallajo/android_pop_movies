package com.shu.popularmoviesstage1;

import java.util.List;

public class MovieData {

    // members
    private int id;
    private String title;
    private int vote_count;
    private boolean video;
    private double vote_average;
    private double popularity;
    private String poste_path;
    private String original_language;
    private String original_title;
    private List<Integer> genre_ids;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private String release_date;

    public MovieData(int id
            , String title
            , int vote_count
            , boolean video
            , double vote_average
            , double popularity
            , String poste_path
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
        this.poste_path = poste_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.genre_ids = genre_ids;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;

    }

    // public methods

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPoste_path() {
        return poste_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }
}
