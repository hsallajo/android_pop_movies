package com.shu.popularmoviesstage1;

import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.parceler.Parcels;
import java.util.Iterator;
import static com.shu.popularmoviesstage1.MainActivity.POP_MOVIES_MOVIE_DETAILS;

public class MovieDetailsActivity extends AppCompatActivity {

    // constants
    public static final String TAG = MovieDetailsActivity.class.getSimpleName();

    MovieData movieData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Parcelable p = getIntent().getParcelableExtra(POP_MOVIES_MOVIE_DETAILS);

        movieData = Parcels.unwrap(p);

        if(movieData == null) {
            Log.d(TAG, "onCreate: movieData has null value");
            finish();
        }

        setTitle(getString(R.string.title_movie_details));

        populateMovieDetailsView();

    }

    private void populateMovieDetailsView(){

        TextView tvTitle = findViewById(R.id.movie_title);
        tvTitle.setText(movieData.title);

        ImageView iv = findViewById(R.id.movie_poster_thumbnail);
        String path = JsonUtils.MOVIE_DB_POSTER_PATH + movieData.poste_path;
        Picasso.with(this)
                .load(path).fit()
                .centerInside()
                .into(iv);

        TextView tvOriginalName= findViewById(R.id.original_name);
        tvOriginalName.setText(movieData.original_title);

        TextView tvLanguage= findViewById(R.id.languages);
        tvLanguage.setText(movieData.original_language);

        TextView tvReleaseYear = findViewById(R.id.release_year);
        tvReleaseYear.setText(movieData.release_date.substring(0,4));

        TextView tvReleaseDate = findViewById(R.id.release_date);
        tvReleaseDate.setText(movieData.release_date);

        TextView tvGenres = findViewById(R.id.genre);
        StringBuilder s = new StringBuilder();
        Iterator<Integer> i = movieData.genre_ids.listIterator();
        while (i.hasNext()) {
            int genreId = i.next();
            String genreStr = JsonUtils.genre_str(genreId);
            if( genreStr != null) {
                s.append(genreStr.substring(0, 1).toUpperCase() + genreStr.substring(1));
            }
            if( i.hasNext() )
                s.append(", ");
        }
        tvGenres.setText(s.toString());

        TextView tvUserRating = findViewById(R.id.user_rating);
        tvUserRating.setText(String.valueOf(movieData.vote_average));
        GradientDrawable bg = (GradientDrawable)tvUserRating.getBackground();
        bg.setColor(getUserRatingColor(movieData.vote_average));

        TextView tvUserVotes = findViewById(R.id.user_votes);
        tvUserVotes.setText(String.valueOf(movieData.vote_count));

        TextView tvPlot = findViewById(R.id.movie_plot);
        tvPlot.setText(movieData.overview);

    }

    private int getUserRatingColor(double userRating) {

        int roundedVal = (int) Math.ceil(userRating);
        int color;

        switch(roundedVal){
            case 0:
            case 1:
            case 2: {
                color = R.color.rating_blue;
                break; }
            case 3:
            case 4:{
                color = R.color.rating_ocean;
                break; }
            case 5:
            case 6:{
                color = R.color.rating_sun;
                break; }
            case 7:
            case 8:{
                color = R.color.rating_orange;
                break; }
            case 9:
            default:{
                color = R.color.rating_read; }
        }
        return ContextCompat.getColor(this, color);
    }
}
