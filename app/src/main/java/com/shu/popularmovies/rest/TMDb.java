package com.shu.popularmovies.rest;

import com.shu.popularmovies.model.MovieDetails;
import com.shu.popularmovies.model.MoviePage;
import com.shu.popularmovies.model.ReviewPage;
import com.shu.popularmovies.model.TrailerPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDb {

    @GET("{sort_option}?language=en-US")
    Call<MoviePage> loadMovies( @Path("sort_option") String sort_option
            , @Query("api_key") String api_key
            , @Query("page") String pgNum);

    @GET("{movie_id}/videos?language=en-US")
    Call<TrailerPage> getTrailers(@Path("movie_id") String movie_id
            , @Query("api_key") String api_key);

    @GET("{movie_id}/reviews?language=en-US")
    Call<ReviewPage> getReviews(@Path("movie_id") String movie_id
            , @Query("api_key") String api_key
            , @Query("page") String pgNum);

    @GET("{movie_id}?language=en-US")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") String movie_id
            , @Query("api_key") String api_key);
}
