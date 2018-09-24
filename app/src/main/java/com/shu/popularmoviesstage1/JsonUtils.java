package com.shu.popularmoviesstage1;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static final String JSON_UTILS = "JsonUtils";


    static List<MovieData> extractMovieData(String jsonString) {
        JSONObject jsonObject;
        List<MovieData> list = new ArrayList<MovieData>();

        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.d(JSON_UTILS, "invalid json string");
            return null;
        }

        JSONArray results = jsonObject.optJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject o = results.optJSONObject(i);
            if( o == null ) {
                Log.d(JSON_UTILS, "invalid json string");
                return null;
            }
            int voteCount = o.optInt("vote_count");
            int id = o.optInt("id");
            boolean video = o.optBoolean("video");
            double voteAverage = o.optDouble("vote_average");
            String title = o.optString("title");
            double popularity = o.optDouble("popularity");
            String posterPath = o.optString("poster_path");
            String originalLanguage = o.optString("original_language");
            String originalTitle = o.optString("original_title");

            JSONArray jsonGenreIds = o.optJSONArray("genre_ids");
            if(jsonGenreIds == null) {
                Log.d(JSON_UTILS, "invalid json string");
                return null;
            }
            List<Integer> genreIds = new ArrayList<>();
            for(int j=0; j<jsonGenreIds.length(); j++){
                genreIds.add(jsonGenreIds.optInt(j));
            }
            String backdropPath = o.optString("backdrop_path");
            boolean adult = o.optBoolean("adult");
            String overview = o.optString("overview");
            String releaseDate = o.optString("release_date");

            if( title == "" || overview == "" || releaseDate == "" ){
                Log.d(JSON_UTILS, "invalid json string");
                return null;
            }

            MovieData d = new MovieData(id
                    , title
                    , voteCount
                    , video
                    , voteAverage
                    , popularity
                    , posterPath
                    , originalLanguage
                    , originalTitle
                    , genreIds
                    , backdropPath
                    , adult
                    , overview
                    , releaseDate);

            list.add(d);
        }

        return list;
    }


    public static int display_width(Context context) {
        float width = context.getResources().getDisplayMetrics().widthPixels
                / context.getResources().getDisplayMetrics().density;
        return (int) width;
    }
}