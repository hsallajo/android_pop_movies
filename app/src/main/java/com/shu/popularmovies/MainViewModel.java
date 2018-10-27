package com.shu.popularmovies;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.shu.popularmovies.model.Movie;
import com.shu.popularmovies.model.MoviePage;
import com.shu.popularmovies.rest.MovieDbAPI;
import com.shu.popularmovies.utils.DataUtilities;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    public static final String TAG = MainViewModel.class.getSimpleName();
    private int lastPage;
    private MutableLiveData<List<Movie>> movieList;

    private DataUtilities.MovieSortOption sortOrderSelection
            = DataUtilities.MovieSortOption.mostPopular;
    private boolean resetCacheOnNextResponse = false;

    public MainViewModel(@NonNull Application application) {
        super(application);

        if (movieList == null) {
            movieList = new MutableLiveData<>();

            resetPage();
            loadMovies();
        }

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

        loadMovies();

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

        MovieDbAPI db = DataUtilities.getMovieDb();
        db.loadMovies(sortOrderSelection.toString(), BuildConfig.MOVIE_DB_USER_API_KEY, Integer.toString(lastPage))
                .enqueue(new Callback<MoviePage>() {
                    @Override
                    public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                        Log.d(TAG, "lifecycle ViewModel onResponse: ");
                        if (response.isSuccessful()) {

                            List<Movie> extractedMovies = response.body().getMovies();
                            if (extractedMovies == null || extractedMovies.isEmpty()) {
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

                            t.addAll(extractedMovies);
                            movieList.setValue(t);

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
}
