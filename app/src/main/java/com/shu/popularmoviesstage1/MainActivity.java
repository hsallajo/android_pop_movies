package com.shu.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.parceler.Parcels;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieListClickListener {

    /**
     * Constants
     */
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NUM_OF_COLUMNS = 3;
    public static final String POP_MOVIE_DETAILS = "MOVIE_DETAILS";
    /**
     * Constant used to estimate if ~2/3 of loaded views have been passed.
     */
    private static final double CONST_TH_LOAD_NEW_MOVIES = 0.67;

    private RecyclerView recyclerView;
    private List<MovieData> movieData;
    private MovieListAdapter movieListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    /**
     * page(s) that have been previously requested
     */
    private int lastPage = 0;
    private JsonUtilities.SortMovieBy sortOrderSelection = JsonUtilities.SortMovieBy.mostPopular;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rc_movies);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);

        movieData = new ArrayList<>();
        movieListAdapter = new MovieListAdapter(movieData, this);
        recyclerView.setAdapter(movieListAdapter);

        loadMovies(sortOrderSelection);

        addEndlessScrolling();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.pop_movies_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.switch_most_popular: {
                if (sortOrderSelection == JsonUtilities.SortMovieBy.mostPopular)
                    return true;
                sortOrderSelection = JsonUtilities.SortMovieBy.mostPopular;
                break;
            }

            case R.id.switch_most_rated: {
                if (sortOrderSelection == JsonUtilities.SortMovieBy.topRated)
                    return true;
                sortOrderSelection = JsonUtilities.SortMovieBy.topRated;
                break;
            }

            default:
        }

        resetMovieData();
        loadMovies(sortOrderSelection);

        return true;
    }

    private void resetMovieData() {

        lastPage = 0;
        movieData.clear();

    }

    private void addEndlessScrolling() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {

                    int numVisibleViews = layoutManager.getChildCount();
                    int numTotalViews = layoutManager.getItemCount();
                    int positionOfFirstVisibleView = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();

                    // if user is scrolling down, estimate if ~2/3 of loaded views have been passed.
                    if ((positionOfFirstVisibleView
                            + numVisibleViews) / numTotalViews > CONST_TH_LOAD_NEW_MOVIES) {
                        // load a new set (page) of views.
                        loadMovies(JsonUtilities.SortMovieBy.topRated);
                    }

                }
            }
        });
    }

    private void loadMovies(JsonUtilities.SortMovieBy sortMovieBy) {

        if (!isOnline()) {
            Toast.makeText(this, R.string.err_msg_no_network, Toast.LENGTH_LONG).show();
            return;
        }

        RequestMoviesAsyncTask task = new RequestMoviesAsyncTask();
        URL query;

        lastPage++;
        query = JsonUtilities.buildMoviesUrl(sortMovieBy, lastPage);

        if (query == null) {
            Log.i(TAG, getString(R.string.err_msg_invalid_query));
            Toast.makeText(this, getString(R.string.err_msg_invalid_query), Toast.LENGTH_LONG).show();
            return;
        }

        task.execute(query);
    }


    @Override
    public void onMovieListItemClick(int position) {

        Intent i = new Intent(this, MovieDetailsActivity.class);

        MovieData data;
        data = movieData.get(position);
        if (data == null)
            return;

        i.putExtra(POP_MOVIE_DETAILS, Parcels.wrap(data));

        startActivity(i);
    }

    private boolean isOnline() {

        ConnectivityManager c =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = c.getActiveNetworkInfo();

        if (info == null)
            return false;

        return (info.isConnectedOrConnecting()
                && info.isConnected());

    }

    class RequestMoviesAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            return JsonUtilities.makeNetworkRequest(urls[0]);
        }

        @Override
        protected void onPostExecute(String queryResult) {

            List<MovieData> extractedMovies;
            extractedMovies = JsonUtilities.extractMovieData(queryResult);

            if (extractedMovies == null || extractedMovies.isEmpty()) {
                //Log.d(TAG, "Query result is null.");
                return;
            }

            movieData.addAll(extractedMovies);
            movieListAdapter.notifyDataSetChanged();

        }
    }
}
