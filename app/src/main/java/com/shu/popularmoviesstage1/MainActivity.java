package com.shu.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.shu.popularmoviesstage1.model.Movie;
import com.shu.popularmoviesstage1.model.MovieDbAPI;
import com.shu.popularmoviesstage1.model.MoviePage;
import com.shu.popularmoviesstage1.utils.DataUtilities;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieListClickListener {

    // Constants
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NUM_OF_COLUMNS = 3;
    public static final String POP_MOVIE_DETAILS = "MOVIE_DETAILS";
    /**
     * Constant used to estimate if ~2/3 of loaded views have been passed.
     */
    private static final double CONST_TH_LOAD_NEW_MOVIES = 0.67;

    private List<Movie> movieData;
    private int lastPage = 0;
    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DataUtilities.MovieSortOption sortOrderSelection
            = DataUtilities.MovieSortOption.mostPopular;

    private MovieDbAPI movieDb;

    private static String dbUserKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbUserKey = BuildConfig.MOVIE_DB_USER_API_KEY;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rc_movies);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);

        movieData = new ArrayList<>();
        movieListAdapter = new MovieListAdapter(movieData, this);
        recyclerView.setAdapter(movieListAdapter);

        movieDb = DataUtilities.getMovieDb();

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
                if (sortOrderSelection == DataUtilities.MovieSortOption.mostPopular)
                    return true;
                sortOrderSelection = DataUtilities.MovieSortOption.mostPopular;
                break;
            }

            case R.id.switch_most_rated: {
                if (sortOrderSelection == DataUtilities.MovieSortOption.topRated)
                    return true;
                sortOrderSelection = DataUtilities.MovieSortOption.topRated;
                break;
            }

            default:
        }

        resetMovieData();
        loadMovies(sortOrderSelection);

        return true;
    }

    @Override
    public void onMovieListItemClick(int position) {

        Intent i = new Intent(this, MovieDetailsActivity.class);

        Movie data;
        data = movieData.get(position);
        if (data == null)
            return;

        i.putExtra(POP_MOVIE_DETAILS, Parcels.wrap(data));

        startActivity(i);
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
                        loadMovies(DataUtilities.MovieSortOption.topRated);
                    }

                }
            }
        });
    }

    private void loadMovies(DataUtilities.MovieSortOption movieSortOpt) {

        if (!isOnline()) {
            Toast.makeText(this, R.string.err_msg_no_network, Toast.LENGTH_LONG).show();
            return;
        }

        lastPage++;

        movieDb.loadMovies(movieSortOpt.toString(), dbUserKey, Integer.toString(lastPage))
                .enqueue(new Callback<MoviePage>() {
                    @Override
                    public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                        if(response.isSuccessful()){
                            updateData(response.body().getMovies());
                        } else {
                            Log.d(TAG, "onResponse: response.code " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviePage> call, Throwable t) {
                        Context c = getApplicationContext();
                        Toast.makeText(c, getString(R.string.err_msg_invalid_query), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onFailure: " + t);
                    }
                });

    }

    private boolean isOnline() {

        ConnectivityManager c =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (c == null)
            return false;

        NetworkInfo info = c.getActiveNetworkInfo();

        return (info != null
                && info.isConnectedOrConnecting()
                && info.isConnected());

    }

    private void updateData(List<Movie> extractedMovies){

        if (extractedMovies == null || extractedMovies.isEmpty()) {
            return;
        }

        movieData.addAll(extractedMovies);
        movieListAdapter.notifyDataSetChanged();
    }
}
