package com.shu.popularmoviesstage1;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.parceler.Parcels;

import static com.shu.popularmoviesstage1.MainActivity.POP_MOVIES_MOVIE_DETAILS;

public class MovieDetailsActivity extends AppCompatActivity {

    MovieData data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Parcelable p = getIntent().getParcelableExtra(POP_MOVIES_MOVIE_DETAILS);

        data = Parcels.unwrap(p);

        if(data == null) {
            // todo
            // hide info fields, show err icon indicating midding info / link
            return;
        }

        //TextView tv_title = findViewById(R.id.movie_title);
        //tv_title.setText(data.getTitle());
        setTitle(data.getTitle());
    }
}
