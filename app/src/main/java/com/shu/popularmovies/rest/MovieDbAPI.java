package com.shu.popularmovies.rest;

import com.shu.popularmovies.model.MoviePage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbAPI {

    @GET("{sort_option}?language=en-US")
    Call<MoviePage> loadMovies( @Path("sort_option") String sort_option
            , @Query("api_key") String api_key
            , @Query("page") String pgNum);
}
