package com.shu.popularmoviesstage1;

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

    private final static String TAG = JsonUtilities.class.getSimpleName();
    private final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie";
    public final static String MOVIE_DB_POSTER_PATH = "https://image.tmdb.org/t/p/w185";
    private final static String QUERY_ACTION_POPULAR = "/popular";
    private final static String QUERY_ACTION_TOP_RATED = "/top_rated";

    private final static String MOVIE_DB_USER_LANG = "en-US";
    private final static String API_KEY_PARAM = "api_key";
    private final static String LANG_PARAM = "language";
    private final static String PAGE_PARAM = "page";
    private final static String ERR_MSG_INVALID_JSON_STR = "Invalid json string";
    private final static String ERR_MSG_BUILDING_URL = "Error building URL.";
    private static final String ERR_MSG_CONNECTION_FAILED = "Connection failed";

    private static final String JSON_KEY_RESULTS = "results";
    private static final String JSON_KEY_VOTE_CNT = "vote_count";
    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_VIDEO = "video";
    private static final String JSON_KEY_VOTE_AVG = "vote_average";
    private static final String JSON_KEY_TITLE = "title";
    private static final String JSON_KEY_POPULARITY = "popularity";
    private static final String JSON_KEY_POSTER_PATH = "poster_path";
    private static final String JSON_KEY_ORI_LANGUAGE = "original_language";
    private static final String JSON_KEY_ORI_TITLE = "original_title";
    private static final String JSON_KEY_GENRE_IDS = "genre_ids";
    private static final String JSON_KEY_BACKDROP_PATH = "backdrop_path";
    private static final String JSON_KEY_ADULT = "adult";
    private static final String JSON_KEY_OVERVIEW = "overview";
    private static final String JSON_KEY_REL_DATE = "release_date";

    private static final String HTTP_REQ_GET = "GET";
    private static final int HTTP_TIMEOUT = 10000;
    private static final int HTTP_RESP_OK = 200;
    private static final String CHARSET_UTF_8 = "UTF-8";

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

    enum SortMovieBy {

        mostPopular,
        topRated

    }


    public static URL buildMoviesUrl(SortMovieBy sortParam, int page) {

        String s;

        if (sortParam == SortMovieBy.mostPopular)
            s = MOVIE_DB_BASE_URL + QUERY_ACTION_POPULAR;
        else
            s = MOVIE_DB_BASE_URL + QUERY_ACTION_TOP_RATED;

        if (MOVIE_DB_USER_API_KEY.equals(""))
            return null;

        Uri uri = Uri.parse(s).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, MOVIE_DB_USER_API_KEY)
                .appendQueryParameter(LANG_PARAM, MOVIE_DB_USER_LANG)
                .appendQueryParameter(PAGE_PARAM, Integer.toString(page))
                .build();

        URL url;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.d(TAG, ERR_MSG_BUILDING_URL);
            return null;
        }

        //Log.i(TAG, "Request url: " + url);

        return url;
    }

    public static List<MovieData> extractMovieData(String jsonString) {

        JSONObject jsonObject;
        List<MovieData> list = new ArrayList<>();

        try {
            if (jsonString != null) {
                jsonObject = new JSONObject(jsonString);
            } else {
                Log.d(TAG, ERR_MSG_INVALID_JSON_STR);
                return null;
            }
        } catch (JSONException e) {
            Log.d(TAG, ERR_MSG_INVALID_JSON_STR);
            return null;
        }
        //Log.i(TAG, "Json response: " + jsonString);

        JSONArray results = jsonObject.optJSONArray(JSON_KEY_RESULTS);

        for (int i = 0; i < results.length(); i++) {

            JSONObject o = results.optJSONObject(i);

            if (o == null) {
                Log.d(TAG, ERR_MSG_INVALID_JSON_STR);
                return null;
            }

            int voteCount = o.optInt(JSON_KEY_VOTE_CNT);
            int id = o.optInt(JSON_KEY_ID);
            boolean video = o.optBoolean(JSON_KEY_VIDEO);
            double voteAverage = o.optDouble(JSON_KEY_VOTE_AVG);
            String title = o.optString(JSON_KEY_TITLE);
            double popularity = o.optDouble(JSON_KEY_POPULARITY);
            String posterPath = o.optString(JSON_KEY_POSTER_PATH);
            String originalLanguage = o.optString(JSON_KEY_ORI_LANGUAGE);
            String originalTitle = o.optString(JSON_KEY_ORI_TITLE);

            JSONArray jsonGenreIds = o.optJSONArray(JSON_KEY_GENRE_IDS);
            if (jsonGenreIds == null) {
                Log.d(TAG, ERR_MSG_INVALID_JSON_STR);
                return null;
            }

            List<Integer> genreIds = new ArrayList<>();
            for (int j = 0; j < jsonGenreIds.length(); j++) {
                genreIds.add(jsonGenreIds.optInt(j));
            }

            String backdropPath = o.optString(JSON_KEY_BACKDROP_PATH);
            boolean adult = o.optBoolean(JSON_KEY_ADULT);
            String overview = o.optString(JSON_KEY_OVERVIEW);
            String releaseDate = o.optString(JSON_KEY_REL_DATE);

            if (title.equals("") || overview.equals("") || releaseDate.equals("")) {
                Log.d(TAG, ERR_MSG_INVALID_JSON_STR);
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

    public static String makeNetworkRequest(URL url) {
        String jsonResponse = null;

        if (url == null)
            return null;

        HttpURLConnection connection;
        InputStream input = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Log.d(TAG, ERR_MSG_CONNECTION_FAILED);
            return null;
        }

        try {
            connection.setReadTimeout(HTTP_TIMEOUT);
            connection.setConnectTimeout(HTTP_TIMEOUT);
            connection.setRequestMethod(HTTP_REQ_GET);
            connection.connect();

            if (connection.getResponseCode() == HTTP_RESP_OK) {
                input = connection.getInputStream();
                jsonResponse = readFromInputStream(input);
            } else {
                Log.d(TAG, "Connection error: " + connection.getResponseCode());
                return null;
            }
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

            InputStreamReader inReader = new InputStreamReader(in, Charset.forName(CHARSET_UTF_8));
            BufferedReader bReader = new BufferedReader(inReader);
            String line = bReader.readLine();

            while (line != null) {
                builder.append(line);
                line = bReader.readLine();
            }
        }

        return builder.toString();
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