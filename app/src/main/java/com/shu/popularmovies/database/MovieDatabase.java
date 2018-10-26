package com.shu.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MovieEntry.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String TAG = MovieDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DBNAME = "moviedb";
    private static MovieDatabase sInstance;

    public static MovieDatabase getInstance(Context context){
        if (sInstance == null ){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext()
                        , MovieDatabase.class
                        , MovieDatabase.DBNAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
