package com.shu.popularmovies;

import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.shu.popularmovies.model.Movie;
import com.shu.popularmovies.utils.DataUtilities;
import com.squareup.picasso.Picasso;
import org.parceler.Parcels;
import java.util.Iterator;
import static com.shu.popularmovies.MainActivity.POP_MOVIE_DETAILS;

public class MovieDetailsActivity extends AppCompatActivity {

    // Constants
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private Movie movieData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Parcelable p = getIntent().getParcelableExtra(POP_MOVIE_DETAILS);

        movieData = Parcels.unwrap(p);

        if(movieData == null) {
            Log.d(TAG, "onCreate: movieData has null value");
            finish();
        }

        setTitle(getString(R.string.title_movie_details));

        populateMovieDetailsView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case android.R.id.home: {
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateMovieDetailsView(){

        TextView tvTitle = findViewById(R.id.movie_title);
        tvTitle.setText(movieData.getTitle());

        ImageView iv = findViewById(R.id.movie_poster_thumbnail);
        String path = DataUtilities.MOVIE_DB_POSTER_PATH + movieData.getPosterPath();
        Picasso.with(this)
                .load(path).fit()
                .centerInside()
                .into(iv);

        TextView tvOriginalName= findViewById(R.id.original_name);
        tvOriginalName.setText(movieData.getOriginalTitle());

        TextView tvLanguage= findViewById(R.id.languages);
        tvLanguage.setText(movieData.getOriginalLanguage());

        TextView tvReleaseYear = findViewById(R.id.release_year);
        tvReleaseYear.setText(movieData.getReleaseDate().substring(0,4));

        TextView tvReleaseDate = findViewById(R.id.release_date);
        tvReleaseDate.setText(movieData.getReleaseDate());

        TextView tvGenres = findViewById(R.id.genre);
        StringBuilder s = new StringBuilder();

        Iterator<Integer> i = movieData.getGenreIds().listIterator();
        while (i.hasNext()) {

            int genreId = i.next();
            String genreStr = DataUtilities.genre(genreId);
            if( genreStr != null) {
                String capitalized = genreStr.substring(0, 1).toUpperCase() + genreStr.substring(1);
                s.append(capitalized);
            }
            if( i.hasNext() )
                s.append(", ");
        }
        tvGenres.setText(s.toString());


        TextView tvUserRating = findViewById(R.id.user_rating);
        tvUserRating.setText(String.valueOf(movieData.getVoteAverage()));
        GradientDrawable bg = (GradientDrawable)tvUserRating.getBackground();
        bg.setColor(
                getColorForUserRating(movieData.getVoteAverage())
        );

        TextView tvUserVotes = findViewById(R.id.user_votes);
        tvUserVotes.setText(String.valueOf(movieData.getVoteCount()));

        TextView tvPlot = findViewById(R.id.movie_plot);
        tvPlot.setText(movieData.getOverview());
    }

    private int getColorForUserRating(double userRating) {

        int roundedVal = (int) Math.ceil(userRating);
        int color;

        switch(roundedVal){
            case 0:
            case 1:
            case 2: {
                color = R.color.rating_1;
                break; }
            case 3:
            case 4:{
                color = R.color.rating_2;
                break; }
            case 5:
            case 6:{
                color = R.color.rating_3;
                break; }
            case 7:
            case 8:{
                color = R.color.rating_4;
                break; }
            case 9:
            default:{
                color = R.color.rating_5; }
        }
        return ContextCompat.getColor(this, color);
    }
}
