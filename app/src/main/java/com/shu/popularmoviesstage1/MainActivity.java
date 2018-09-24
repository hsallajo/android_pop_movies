package com.shu.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int NUM_OF_COLUMNS = 3;

    private RecyclerView recyclerView;
    private List<MovieData> movieDataList;
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

        movieDataList = JsonUtils.extractMovieData("");

        movieListAdapter = new MovieListAdapter(movieDataList);
        recyclerView.setAdapter(movieListAdapter);

    }
}
