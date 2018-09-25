package com.shu.popularmoviesstage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.parceler.Parcels;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieListClickListener {

    // constants
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int NUM_OF_COLUMNS = 3;
    public static final String POP_MOVIES_MOVIE_DETAILS = "MOVIE_DETAILS";
    public static final String QUERY = "https://api.themoviedb.org/3/discover/movie?api_key=461211e84e4eae51a2c0b938ee2c169c&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=3";

    private RecyclerView recyclerView;
    private List<MovieData> movieData;
    private MovieListAdapter movieListAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rc_movies);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);

        movieData = new ArrayList<MovieData>();
        movieListAdapter = new MovieListAdapter(movieData, this);
        recyclerView.setAdapter(movieListAdapter);

        RequestMoviesAsyncTask task = new RequestMoviesAsyncTask();

        URL query = null;
        query = JsonUtils.buildMoviesUrl(JsonUtils.SortMovieBy.topRated, 1);
        task.execute(query);

    }

    @Override
    public void onMovieListItemClick(int position) {
        Intent i = new Intent(this, MovieDetailsActivity.class);

        MovieData data = null;
        data = movieData.get(position);
        if (data == null)
            return;

        i.putExtra(POP_MOVIES_MOVIE_DETAILS, Parcels.wrap(data));

        startActivity(i);
    }

    class RequestMoviesAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            Log.d(TAG, "doInBackground: ");
            return JsonUtils.makeNetworkRequest(urls[0]);
        }

        @Override
        protected void onPostExecute(String queryResult) {
            Log.d(TAG, "onPostExecute: " + queryResult);
            List<MovieData> extractedMovies = null;


            extractedMovies = JsonUtils.extractMovieData(queryResult);
            if (extractedMovies.isEmpty() || extractedMovies == null) {
                Log.d(TAG, "Query result is null.");
                return;
            }

            //movieData.addAll(extractedMovies);
            movieData = extractedMovies;
            movieListAdapter.refreshData(movieData);
        }
    }
}
