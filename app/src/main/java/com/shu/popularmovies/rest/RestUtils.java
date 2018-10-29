package com.shu.popularmovies.rest;

import com.shu.popularmovies.BuildConfig;
import com.shu.popularmovies.database.FavMovieEntry;
import com.shu.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RestUtils {

    final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    public final static String MOVIE_DB_POSTER_PATH = "https://image.tmdb.org/t/p/w185";

    public static String dbUserKey = BuildConfig.MOVIE_DB_USER_API_KEY;

    public static TMDb getTMDbInstance(){
        return RetrofitClient.getInstance(MOVIE_DB_BASE_URL).create(TMDb.class);
    }

    public static FavMovieEntry convertToMovieEntry(Movie movieData){
        FavMovieEntry entry = new FavMovieEntry( movieData.getTitle()
                , movieData.getId()
                , movieData.getPosterPath()
                , movieData.getPopularity()
                , movieData.getVideo()? 1 : 0
                , movieData.getVoteCount()
                , movieData.getVoteAverage()
                , movieData.getOriginalLanguage()
                , movieData.getOriginalTitle()
                , ""
                , movieData.getBackdropPath()
                , movieData.getAdult()? 1 : 0
                , movieData.getOverview()
                , movieData.getReleaseDate());

        StringBuilder genres = new StringBuilder();
        Iterator<Integer> i = (Iterator<Integer>) movieData.getGenreIds().iterator();

        if(i != null) {
            while (i.hasNext()) {
                genres.append(i.next().toString());
                if (i.hasNext())
                    genres.append(',');
            }
        }
        entry.setGenreIds(genres.toString());

        return entry;
    }

    public static Movie convertToMovie(FavMovieEntry movieData){
        Movie entry = new Movie();

        entry.setTitle(movieData.getTitle());
        entry.setId(movieData.getMovieId());
        entry.setPosterPath(movieData.getPosterPath());
        entry.setPopularity(movieData.getPopularity());
        if(movieData.getVideo() == 1) entry.setVideo(true); else entry.setVideo(false);
        entry.setVoteCount(movieData.getVoteCount());
        entry.setVoteAverage(movieData.getVoteAverage());
        entry.setOriginalLanguage(movieData.getOriginalLanguage());
        entry.setOriginalTitle(movieData.getOriginalTitle());
        entry.setBackdropPath(movieData.getBackdropPath());
        if(movieData.getAdult() == 1) entry.setAdult(true); else entry.setAdult(false);
        entry.setOverview(movieData.getOverview());
        entry.setReleaseDate(movieData.getReleaseDate());

        List<Integer> newList = new ArrayList<Integer>();

        String genres = movieData.getGenreIds();
        if(genres != null) {
            String[] values = genres.split(",");
            for(int i = 0; i < values.length; i++){
                newList.add(Integer.valueOf(values[i]));
            }
        }
        entry.setGenreIds(newList);

        return entry;
    }

}
