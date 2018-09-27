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
    final static String ERR_MSG_INVALID_JSON_STR = "Invalid json string";
    final static String ERR_MSG_BUILDING_URL = "Error building URL.";
    static final String ERR_MSG_CONNECTION_FAILED = "Connection failed";
    
    static final String JSON_KEY_RESULTS = "results";
    static final String JSON_KEY_VOTE_CNT = "vote_count";
    static final String JSON_KEY_ID = "id";
    static final String JSON_KEY_VIDEO = "video";
    static final String JSON_KEY_VOTE_AVG = "vote_average";
    static final String JSON_KEY_TITLE = "title";
    static final String JSON_KEY_POPULARITY = "popularity";
    static final String JSON_KEY_POSTER_PATH = "poster_path";
    static final String JSON_KEY_ORI_LANGUAGE = "original_language";
    static final String JSON_KEY_ORI_TITLE = "original_title";
    static final String JSON_KEY_GENRE_IDS = "genre_ids";
    static final String JSON_KEY_BACKDROP_PATH = "backdrop_path";
    static final String JSON_KEY_ADULT = "adult";
    static final String JSON_KEY_OVERVIEW = "overview";
    static final String JSON_KEY_REL_DATE = "release_date";

    static final String HTTP_REQ_GET = "GET";
    static final int HTTP_TIMEOUT = 10000;
    static final int HTTP_RESP_OK = 200;
    static final String CHARSET_UTF_8 = "UTF-8";

    static final String GENRE_ACTION = "Action";
    static final String GENRE_ADVENTURE = "Adventure";
    static final String GENRE_ANIMATION = "Animation";
    static final String GENRE_COMEDY = "Comedy";
    static final String GENRE_CRIME = "Crime";
    static final String GENRE_DOCUMENTARY = "Documentary";
    static final String GENRE_DRAMA = "Drama";
    static final String GENRE_FAMILY = "Family";
    static final String GENRE_FANTASY = "Fantasy";
    static final String GENRE_HISTORY = "History";
    static final String GENRE_HORROR = "Horror";
    static final String GENRE_MUSIC = "Music";
    static final String GENRE_MYSTERY = "Mystery";
    static final String GENRE_ROMANCE = "Romance";
    static final String GENRE_SCIENCE_FICTION = "Science Fiction";
    static final String GENRE_TV_MOVIE = "TV Movie";
    static final String GENRE_THRILLER = "Thriller";
    static final String GENRE_WAR = "War";
    static final String GENRE_WESTERN = "Western";

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

        if(MOVIE_DB_USER_API_KEY == "")
            return null;

        Uri uri = Uri.parse(s).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, MOVIE_DB_USER_API_KEY)
                .appendQueryParameter(LANG_PARAM, MOVIE_DB_USER_LANG)
                .appendQueryParameter(PAGE_PARAM, Integer.toString(page))
                .build();

        URL url = null;
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
        List<MovieData> list = new ArrayList<MovieData>();

        try {
            if (jsonString != null || jsonString.length() != 0) {
                jsonObject = new JSONObject(jsonString);
            }
            else {
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

            if (title == "" || overview == "" || releaseDate == "") {
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


    /*public static int display_width(Context context) {
        float width = context.getResources().getDisplayMetrics().widthPixels
                / context.getResources().getDisplayMetrics().density;
        return (int) width;
    }*/

    public static String makeNetworkRequest(URL url) {
        String jsonResponse = null;

        if (url == null)
            return jsonResponse;

        HttpURLConnection connection = null;
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

    public static String genre_str(int id) {

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
};