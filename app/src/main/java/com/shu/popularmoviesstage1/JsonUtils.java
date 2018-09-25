package com.shu.popularmoviesstage1;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    // constants
    //public static final String QUERY = "https://api.themoviedb.org/3/movie/popular?api_key=461211e84e4eae51a2c0b938ee2c169c&language=en-US&sort_by=popularity.desc&include_video=false&page=3";
    final static String TAG = JsonUtils.class.getSimpleName();
    final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String QUERY_ACTION_POPULAR = "/popular";
    final static String QUERY_ACTION_TOP_RATED= "/top_rated";
    final static String MOVIE_DB_USER_API_KEY = "461211e84e4eae51a2c0b938ee2c169c";
    final static String MOVIE_DB_USER_LANG = "en-US";
    final static String API_KEY_PARAM = "api_key";
    final static String LANG_PARAM = "language";
    final static String PAGE_PARAM = "page";

    enum SortMovieBy{
        mostPopular,
        topRated
    }

    public static URL buildMoviesUrl(SortMovieBy sortParam, int page){
        String s = null;

        if(sortParam == SortMovieBy.mostPopular)
            s = MOVIE_DB_BASE_URL + QUERY_ACTION_POPULAR;
        else
            s = MOVIE_DB_BASE_URL + QUERY_ACTION_TOP_RATED;

        Uri uri = Uri.parse(s).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, MOVIE_DB_USER_API_KEY)
                .appendQueryParameter(LANG_PARAM, MOVIE_DB_USER_LANG)
                .appendQueryParameter(PAGE_PARAM, Integer.toString(page))
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.d(TAG, "Error building URL.");
            return  null;
        }

        Log.d(TAG, "buildRequestMoviesUrl: " + uri.toString());
        return  url;
    }
    public static List<MovieData> extractMovieData(String jsonString) {
        JSONObject jsonObject;
        List<MovieData> list = new ArrayList<MovieData>();

        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.d(TAG, "invalid json string");
            return null;
        }

        JSONArray results = jsonObject.optJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject o = results.optJSONObject(i);
            if( o == null ) {
                Log.d(TAG, "invalid json string");
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
                Log.d(TAG, "invalid json string");
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
                Log.d(TAG, "invalid json string");
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

    public static String makeNetworkRequest(URL url) {
        String jsonResponse = null;

        if(url == null)
            return jsonResponse;

        HttpURLConnection connection = null;
        InputStream input = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Log.d(TAG, "Connection failed");
            return null;
        }

        try {
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == 200){
                input = connection.getInputStream();
                //String s = readFromInputStream(i);
                jsonResponse = readFromInputStream(input);
            } else {
                Log.e(TAG, "Error response code: " + connection.getResponseCode());
            };
        } catch (IOException e) {
            Log.d(TAG, "Problem retrieving movie results. " + e);
        } finally {
            if (connection != null)
                connection.disconnect();
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream in) throws IOException {

        StringBuilder builder = new StringBuilder();
        if (in != null) {
            InputStreamReader inReader = new InputStreamReader(in, Charset.forName("UTF-8"));
            BufferedReader bReader = new BufferedReader(inReader);
            String line = bReader.readLine();
            while(line != null){
                builder.append(line);
                line = bReader.readLine();
            }
        }
        return builder.toString();
    }


}