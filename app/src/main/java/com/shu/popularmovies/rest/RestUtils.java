package com.shu.popularmovies.rest;

import com.shu.popularmovies.BuildConfig;

public class RestUtils {

    final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    public final static String MOVIE_DB_POSTER_PATH = "https://image.tmdb.org/t/p/w185";

    public static String dbUserKey = BuildConfig.MOVIE_DB_USER_API_KEY;

    public static TMDb getTMDbInstance(){
        return RetrofitClient.getInstance(MOVIE_DB_BASE_URL).create(TMDb.class);
    }

}
