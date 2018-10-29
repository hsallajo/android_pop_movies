package com.shu.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.shu.popularmovies.model.Movie;
import com.shu.popularmovies.utils.DataUtilities;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

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

    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DataUtilities.MovieSortOption sortOrderSelection
            = DataUtilities.MovieSortOption.mostPopular;

    MainViewModel model;
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "lifecycle onCreate: ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rc_movies);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);

        movieData = new ArrayList<>();

        movieListAdapter = new MovieListAdapter(movieData, this);
        recyclerView.setAdapter(movieListAdapter);

        //movieDb = DataUtilities.getTMDbInstance();

        model = ViewModelProviders.of(this).get(MainViewModel.class);

        model.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

                refreshCache(movies);
                loading = false;
            }
        });

        addEndlessScrolling();

    }

    private void refreshCache(List<Movie> extractedMovies) {

        if (extractedMovies == null || extractedMovies.isEmpty()) {
            return;
        }

        movieData.clear();
        movieData.addAll(extractedMovies);
        movieListAdapter.notifyDataSetChanged();
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

        model.setMovieSortOption(sortOrderSelection);

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


    private void addEndlessScrolling() {

        if (loading)
            return;

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
                        loading = true;
                        model.nextPage();
                    }

                }
            }
        });
    }


    // todo: remove
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "lifecycle onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "lifecycle onPause: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "lifecycle onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, " lifecycle onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "lifecycle onRestart: ");
    }
}
