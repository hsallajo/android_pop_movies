package com.shu.popularmovies;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.shu.popularmovies.database.FavMovieEntry;
import com.shu.popularmovies.database.FavMoviesDatabase;
import com.shu.popularmovies.model.Movie;
import com.shu.popularmovies.model.MoviePage;
import com.shu.popularmovies.rest.RestUtils;
import com.shu.popularmovies.rest.TMDb;
import com.shu.popularmovies.utils.AppExecutors;
import com.shu.popularmovies.utils.DataUtilities;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private int lastPage;
    private MutableLiveData<List<Movie>> movieList;

    private DataUtilities.MovieSortOption sortOrderSelection
            = DataUtilities.MovieSortOption.mostPopular;
    private boolean resetCacheOnNextResponse = false;

    // Favorite movies database
    private List<FavMovieEntry> entries;
    private final FavMoviesDatabase database;

    private LiveData<List<FavMovieEntry>> entryList;

    public MainViewModel(@NonNull Application application) {
        super(application);

        if (movieList == null) {
            movieList = new MutableLiveData<>();

            resetPage();
            loadMovies();
        }

        database = FavMoviesDatabase.getInstance(application.getApplicationContext());

        entryList = database.favMovieDao().loadAllMovies();
        entryList = Transformations.map(entryList,

                new Function< List<FavMovieEntry>, List<FavMovieEntry>>(){
                    @Override
                    public List<FavMovieEntry> apply(List<FavMovieEntry> input) {
                        // refresh cache
                        if (sortOrderSelection == DataUtilities.MovieSortOption.favorite)
                            refresh(input);

                        return input;
                    }
                });
    }

    public LiveData<List<FavMovieEntry>> getFavMovies() {
        return entryList;
    }


    public LiveData<List<Movie>> getMovies() {
        return movieList;
    }

    public void setMovieSortOption(DataUtilities.MovieSortOption movieSortOpt) {

        if (movieSortOpt == sortOrderSelection)
            return;

        resetPage();
        resetCacheOnNextResponse = true;

        sortOrderSelection = movieSortOpt;

        if (sortOrderSelection != DataUtilities.MovieSortOption.favorite)
            loadMovies();
        else
            loadMoviesFromFavsDatabase();
    }

    private void resetPage() {
        lastPage = 1;
    }

    public void nextPage() {
        lastPage++;
        loadMovies();
    }

    private void loadMovies() {

        Context c = this.getApplication().getApplicationContext();
        if (!DataUtilities.isOnline(c)) {
            return;
        }

        TMDb db = RestUtils.getTMDbInstance();
        db.loadMovies(sortOrderSelection.toString(), BuildConfig.MOVIE_DB_USER_API_KEY, Integer.toString(lastPage))
                .enqueue(new Callback<MoviePage>() {
                    @Override
                    public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                List<Movie> extractedMovies = response.body().getMovies();
                                refreshCache(extractedMovies);
                            }

                        } else {
                            Log.d(TAG, "onResponse: response.code " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviePage> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t);
                    }
                });
    }

    private void loadMoviesFromFavsDatabase() {

        if (sortOrderSelection != DataUtilities.MovieSortOption.favorite)
            return;

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                entries = database.favMovieDao().loadAll();

                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        refresh(entries);
                    }
                });

            }
        });
    }

    private void refresh(List<FavMovieEntry> e ){

        List<Movie> extractedMovies = convert(e);
        refreshCache(extractedMovies);

    }

    private void refreshCache(List<Movie> e ){

        if (e == null || e.isEmpty()) {
            return;
        }

        List<Movie> t = movieList.getValue();

        if (t == null) {
            t = new ArrayList<>();
        }

        // resetCacheOnNextResponse if sort order has changed
        if (resetCacheOnNextResponse) {
            resetCacheOnNextResponse = false;
            t.clear();
        }

        t.addAll(e);
        movieList.setValue(t);
    }

    private List<Movie> convert(List<FavMovieEntry> favMovieEntries) {
        List<Movie> l = new ArrayList<>();
        for (FavMovieEntry entry : favMovieEntries
                ) {
            Movie m = RestUtils.convertToMovie(entry);
            l.add(m);
        }
        return l;
    }

}
