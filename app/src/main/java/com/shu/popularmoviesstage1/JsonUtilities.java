package com.shu.popularmoviesstage1;

import android.content.Context;
import android.net.Uri;
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
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.shu.popularmoviesstage1.DbConfig.MOVIE_DB_USER_API_KEY;


public class JsonUtilities {

    // constants
    final static String TAG = JsonUtilities.class.getSimpleName();
    final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String MOVIE_DB_POSTER_PATH = "https://image.tmdb.org/t/p/w185";
    final static String QUERY_ACTION_POPULAR = "/popular";
    final static String QUERY_ACTION_TOP_RATED = "/top_rated";

    final static String MOVIE_DB_USER_LANG = "en-US";
    final static String API_KEY_PARAM = "api_key";
    final static String LANG_PARAM = "language";
    final static String PAGE_PARAM = "page";

    enum SortMovieBy {
        mostPopular,
        topRated
    }


    public static URL buildMoviesUrl(SortMovieBy sortParam, int page) {
        String s = null;

        if (sortParam == SortMovieBy.mostPopular)
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
            return null;
        }

        Log.i(TAG, "Request url: " + url);

        return url;
    }

    public static List<MovieData> extractMovieData(String jsonString) {
        JSONObject jsonObject;
        List<MovieData> list = new ArrayList<MovieData>();

        try {
            if (jsonString != null || jsonString.length() != 0) {
                jsonObject = new JSONObject(jsonString);
            }
            else {
                Log.d(TAG, "invalid json string");
                return null;
            }
        } catch (JSONException e) {
            Log.d(TAG, "invalid json string");
            return null;
        }

        Log.i(TAG, "json response: " + jsonString);

        JSONArray results = jsonObject.optJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject o = results.optJSONObject(i);
            if (o == null) {
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
            if (jsonGenreIds == null) {
                Log.d(TAG, "invalid json string");
                return null;
            }
            List<Integer> genreIds = new ArrayList<>();
            for (int j = 0; j < jsonGenreIds.length(); j++) {
                genreIds.add(jsonGenreIds.optInt(j));
            }
            String backdropPath = o.optString("backdrop_path");
            boolean adult = o.optBoolean("adult");
            String overview = o.optString("overview");
            String releaseDate = o.optString("release_date");

            if (title == "" || overview == "" || releaseDate == "") {
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

        if (url == null)
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

            if (connection.getResponseCode() == 200) {
                input = connection.getInputStream();
                jsonResponse = readFromInputStream(input);
            } else {
                Log.e(TAG, "Error response code: " + connection.getResponseCode());
            }
            ;
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
            while (line != null) {
                builder.append(line);
                line = bReader.readLine();
            }
        }
        return builder.toString();
    }

    public static String genre_str(int id) {

        switch (id) {
            case 28:
                return "Action";

            case 12:
                return "Adventure";

            case 16:
                return "Animation";

            case 35:
                return "Comedy";

            case 80:
                return "Crime";

            case 99:
                return "Documentary";

            case 18:
                return "Drama";

            case 10751:
                return "Family";

            case 14:
                return "Fantasy";

            case 36:
                return "History";

            case 27:
                return "Horror";

            case 10402:
                return "Music";

            case 9648:
                return "Mystery";

            case 10749:
                return "Romance";

            case 878:
                return "Science Fiction";

            case 10770:
                return "TV Movie";

            case 53:
                return "Thriller";

            case 10752:
                return "War";

            case 37:
                return "Western";

            default:
                return null;
        }
    }
};