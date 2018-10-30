package com.shu.popularmovies;

import android.app.Application;
import android.support.annotation.NonNull;
import com.shu.popularmovies.database.FavMovieEntry;
import com.shu.popularmovies.database.FavMoviesDatabase;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavoritesViewModel extends AndroidViewModel {
    public static final String TAG = FavoritesViewModel.class.getSimpleName();

    private final LiveData<List<FavMovieEntry>> entries;


    public FavoritesViewModel(@NonNull Application application){
        super(application);

        final FavMoviesDatabase database = FavMoviesDatabase.getInstance(application.getApplicationContext());
        entries = database.favMovieDao().loadAllMovies();
    }

    public LiveData<List<FavMovieEntry>> getFavorites(){
        return entries;
    }

}
