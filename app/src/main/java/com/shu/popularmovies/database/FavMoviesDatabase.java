package com.shu.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavMovieEntry.class}, version = 1, exportSchema = false)
public abstract class FavMoviesDatabase extends RoomDatabase {

    private static final String TAG = FavMoviesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DBNAME = "moviedb";
    private static FavMoviesDatabase sInstance;

    public static FavMoviesDatabase getInstance(Context context){
        if (sInstance == null ){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext()
                        , FavMoviesDatabase.class
                        , FavMoviesDatabase.DBNAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract FavMoviesDao favMovieDao();
}
