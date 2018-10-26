package com.shu.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Parcelable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shu.popularmovies.model.Movie;
import com.shu.popularmovies.model.MovieDetails;
import com.shu.popularmovies.model.MovieGenre;
import com.shu.popularmovies.model.Review;
import com.shu.popularmovies.model.ReviewPage;
import com.shu.popularmovies.model.Trailer;
import com.shu.popularmovies.model.TrailerPage;
import com.shu.popularmovies.utils.DataUtilities;
import com.squareup.picasso.Picasso;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shu.popularmovies.MainActivity.POP_MOVIE_DETAILS;

public class MovieDetailsActivity extends AppCompatActivity {

    // Constants
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    private Movie movieData = null;

    private List<Trailer> movieTrailers = null;

    private List<Review> movieReviews = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieTrailers = new ArrayList<Trailer>();

        movieReviews = new ArrayList<Review>();

        Parcelable p = getIntent().getParcelableExtra(POP_MOVIE_DETAILS);

        movieData = Parcels.unwrap(p);

        if(movieData == null) {
            finish();
        }

        setTitle(getString(R.string.title_movie_details));

        populateMovieDetailsView();

        int id = movieData.getId();

        Log.d(TAG, "Movie name and id: " + movieData.getTitle() + ", " + id);

        loadMovieDetails(id);
        
        loadMovieTrailers(id);

        loadMovieReviews(id);
    }

    private void loadMovieDetails(int id) {

        Call<MovieDetails> res = DataUtilities.getMovieDb().getMovieDetails(Integer.toString(id), DataUtilities.dbUserKey);

        res.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if(response.isSuccessful()){
                    List<MovieGenre> g = response.body().getGenres();
                    Iterator<MovieGenre> i = g.iterator();
                    while(i.hasNext()){
                        MovieGenre genre = i.next();
                        Log.d(TAG, "genre is: " + genre.getName() + "," + genre.getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });
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
        Picasso.get()
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

    private void loadMovieTrailers( int movieId ){

        Call<TrailerPage> res = DataUtilities.getMovieDb().getTrailers(Integer.toString(movieId), DataUtilities.dbUserKey);

        res.enqueue(new Callback<TrailerPage>() {
            @Override
            public void onResponse(Call<TrailerPage> call, Response<TrailerPage> response) {
                if(response.isSuccessful()){
                    movieTrailers.addAll(response.body().getTrailers());
                    populateMovieTrailers();
                }
            }

            @Override
            public void onFailure(Call<TrailerPage> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void loadMovieReviews( int movieId ){

        int pgNum = 1;

        Call<ReviewPage> res = DataUtilities.getMovieDb().getReviews(Integer.toString(movieId)
                , DataUtilities.dbUserKey
                , Integer.toString(pgNum));

        res.enqueue(new Callback<ReviewPage>() {
            @Override
            public void onResponse(Call<ReviewPage> call, Response<ReviewPage> response) {
                if(response.isSuccessful()){
                    movieReviews.addAll(response.body().getReviews());
                    populateMovieReviews();
                    //Log.d(TAG, "onResponse: populated, number of reviews: " + response.body().getReviews().size() + ", complete.");
                }
            }

            @Override
            public void onFailure(Call<ReviewPage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void populateMovieTrailers(){

        LinearLayout containerLayout = findViewById(R.id.row4);

        Iterator<Trailer> i = (Iterator<Trailer>) movieTrailers.iterator();
        while(i.hasNext()){

            Trailer t = i.next();

            View trailer_layout = LayoutInflater.from(this).inflate(R.layout.trailer_details, containerLayout, false);

            TextView tv_trailer_name = trailer_layout.findViewById(R.id.trailer_name);
            TextView tv_trailer_type = trailer_layout.findViewById(R.id.trailer_type);
            TextView tv_trailer_site = trailer_layout.findViewById(R.id.trailer_site);

            ImageButton btn_trailer_play = (ImageButton) trailer_layout.findViewById(R.id.btn_play_trailer);

            tv_trailer_name.setText(t.getName());
            tv_trailer_type.setText(t.getType());
            tv_trailer_site.setText(t.getSite());

            final String movieKey = t.getKey();

            btn_trailer_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayTrailer(movieKey);
                }
            });

            containerLayout.addView(trailer_layout);

        }

    }

    private void populateMovieReviews(){
        LinearLayout containerLayout = findViewById(R.id.row5);

        Iterator<Review> i = (Iterator<Review>) movieReviews.iterator();

        int cnt = 0;
        while(i.hasNext()){

            Review r = i.next();

            View review_layout = LayoutInflater.from(this).inflate(R.layout.review_details, containerLayout, false);

            TextView tv_review_header = review_layout.findViewById(R.id.review_header);
            TextView tv_review_author = review_layout.findViewById(R.id.review_author);
            TextView tv_review_content = review_layout.findViewById(R.id.review_content);
            TextView tv_review_url = review_layout.findViewById(R.id.review_url);

            tv_review_header.setText(r.getContent());
            tv_review_author.setText(r.getAuthor());
            tv_review_content.setText(r.getContent());
            tv_review_url.setText(r.getUrl());

            containerLayout.addView(review_layout);

            cnt++;
            if(cnt >= 5) break;

        }
    }

    private void PlayTrailer(String key){

        if(key == null || key.equals(""))
            return;

        String url = DataUtilities.YOUTUBE_PATH + key;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
