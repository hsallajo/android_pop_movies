package com.shu.popularmoviesstage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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

    private RecyclerView recyclerView;
    private List<MovieData> movieData;
    private MovieListAdapter movieListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    /** page(s) that have been previously requested */
    private int lastPage = 0;


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

        loadMovies();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if( dy > 0 ){
                    int numVisibleViews = layoutManager.getChildCount();
                    int numTotalViews = layoutManager.getItemCount();
                    int positionOfFirstVisibleView = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();

                    // if user is scrolling down, estimate if ~2/3 of loaded views have been passed.
                    if ( (positionOfFirstVisibleView
                            + numVisibleViews)/numTotalViews > 0.67 ){
                        // load a new set (page) of views.
                        loadMovies();
                    }
                }
            }
        });

    }

    private void loadMovies(){
        RequestMoviesAsyncTask task = new RequestMoviesAsyncTask();
        int nextPage = lastPage + 1;

        URL query = null;
        query = JsonUtils.buildMoviesUrl(JsonUtils.SortMovieBy.topRated, nextPage);
        task.execute(query);

    }


    @Override
    public void onMovieListItemClick(int position) {

        // test
        if(position == 15 || position == 14 || position == 13 ){
            loadMovies();
            return;
        }
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

            movieData.addAll(extractedMovies);
            movieListAdapter.notifyDataSetChanged();
            lastPage ++;
        }
    }
}
