package com.shu.popularmovies.utils;

import com.shu.popularmovies.rest.MovieDbAPI;
import com.shu.popularmovies.rest.RetrofitClient;

public class DataUtilities {

    // Constants
    private final static String TAG = DataUtilities.class.getSimpleName();

    final static String MOVIE_DB_BASE_URL =
            "https://api.themoviedb.org/3/movie/";
    public final static String MOVIE_DB_POSTER_PATH =
            "https://image.tmdb.org/t/p/w185";

    private static final String GENRE_ACTION = "Action";
    private static final String GENRE_ADVENTURE = "Adventure";
    private static final String GENRE_ANIMATION = "Animation";
    private static final String GENRE_COMEDY = "Comedy";
    private static final String GENRE_CRIME = "Crime";
    private static final String GENRE_DOCUMENTARY = "Documentary";
    private static final String GENRE_DRAMA = "Drama";
    private static final String GENRE_FAMILY = "Family";
    private static final String GENRE_FANTASY = "Fantasy";
    private static final String GENRE_HISTORY = "History";
    private static final String GENRE_HORROR = "Horror";
    private static final String GENRE_MUSIC = "Music";
    private static final String GENRE_MYSTERY = "Mystery";
    private static final String GENRE_ROMANCE = "Romance";
    private static final String GENRE_SCIENCE_FICTION = "Science Fiction";
    private static final String GENRE_TV_MOVIE = "TV Movie";
    private static final String GENRE_THRILLER = "Thriller";
    private static final String GENRE_WAR = "War";
    private static final String GENRE_WESTERN = "Western";

    public enum MovieSortOption {

        mostPopular("popular"),
        topRated("top_rated");

        private String sortOption;

        MovieSortOption(String opt){
            this.sortOption = opt;
        }

        public String toString(){
            return sortOption;
        }
    }

    public static MovieDbAPI getMovieDb(){
        return RetrofitClient.getInstance(MOVIE_DB_BASE_URL).create(MovieDbAPI.class);
    }

    public static String genre(int id) {

        switch (id) {
            case 28:
                return GENRE_ACTION;

            case 12:
                return GENRE_ADVENTURE;

            case 16:
                return GENRE_ANIMATION;

            case 35:
                return GENRE_COMEDY;

            case 80:
                return GENRE_CRIME;

            case 99:
                return GENRE_DOCUMENTARY;

            case 18:
                return GENRE_DRAMA;

            case 10751:
                return GENRE_FAMILY;

            case 14:
                return GENRE_FANTASY;

            case 36:
                return GENRE_HISTORY;

            case 27:
                return GENRE_HORROR;

            case 10402:
                return GENRE_MUSIC;

            case 9648:
                return GENRE_MYSTERY;

            case 10749:
                return GENRE_ROMANCE;

            case 878:
                return GENRE_SCIENCE_FICTION;

            case 10770:
                return GENRE_TV_MOVIE;

            case 53:
                return GENRE_THRILLER;

            case 10752:
                return GENRE_WAR;

            case 37:
                return GENRE_WESTERN;

            default:
                return null;
        }
    }
}