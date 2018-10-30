package com.shu.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Parcelable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shu.popularmovies.database.FavMovieEntry;
import com.shu.popularmovies.database.FavMoviesDatabase;
import com.shu.popularmovies.model.Movie;
import com.shu.popularmovies.model.Review;
import com.shu.popularmovies.model.ReviewPage;
import com.shu.popularmovies.model.Trailer;
import com.shu.popularmovies.model.TrailerPage;
import com.shu.popularmovies.rest.RestUtils;
import com.shu.popularmovies.utils.AppExecutors;
import com.shu.popularmovies.utils.DataUtilities;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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

    private boolean isFavMovie = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieTrailers = new ArrayList<>();
        movieReviews = new ArrayList<>();

        Parcelable p = getIntent().getParcelableExtra(POP_MOVIE_DETAILS);

        movieData = Parcels.unwrap(p);

        if (movieData == null) {
            finish();
        }

        setTitle(getString(R.string.title_movie_details));

        populateMovieDetailsView();
        loadMovieTrailers(movieData.getId());
        loadMovieReviews(movieData.getId());
        loadFavorites();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: {
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onToggleStar(View v) {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                if (isFavMovie) {
                    FavMoviesDatabase.getInstance(getApplicationContext()).favMovieDao().deleteByMovieId(movieData.getId());
                } else {
                    final FavMovieEntry entry = RestUtils.convertToMovieEntry(movieData);

                    FavMoviesDatabase.getInstance(getApplicationContext()).favMovieDao().insert(entry);

                }
            }
        });

    }

    private void loadFavorites() {
        FavoritesViewModel model = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        model.getFavorites().observe(this, new Observer<List<FavMovieEntry>>() {
            @Override
            public void onChanged(List<FavMovieEntry> movieEntries) {
                setFavorite(containsId(movieEntries, movieData.getId()));
            }
        });
    }

    private void setFavorite(boolean isFavorite) {

        ImageButton btn = findViewById(R.id.btn_favorite);

        if (isFavorite) {
            isFavMovie = true;
            btn.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            isFavMovie = false;
            btn.setImageResource(android.R.drawable.btn_star_big_off);
        }

    }

    private boolean containsId(final List<FavMovieEntry> list, final int id) {

        for (FavMovieEntry entry : list
                ) {
            if (id == entry.getMovieId())
                return true;
        }
        return false;
    }

    private void populateMovieDetailsView() {

        TextView tvTitle = findViewById(R.id.movie_title);
        tvTitle.setText(movieData.getTitle());

        ImageView iv = findViewById(R.id.movie_poster_thumbnail);
        String path = RestUtils.MOVIE_DB_POSTER_PATH + movieData.getPosterPath();
        Picasso.get()
                .load(path).fit()
                .centerInside()
                .into(iv);

        String backdropPath = RestUtils.MOVIE_DB_BACKDROP_PATH + movieData.getBackdropPath();
        ImageView header_img = findViewById(R.id.movie_backdrop_img);
        Picasso.get()
                .load(backdropPath).fit()
                .centerCrop()
                .into(header_img);

        TextView tvOriginalName = findViewById(R.id.original_name);
        tvOriginalName.setText(movieData.getOriginalTitle());

        TextView tvLanguage = findViewById(R.id.languages);
        tvLanguage.setText(movieData.getOriginalLanguage());

        TextView tvReleaseYear = findViewById(R.id.release_year);
        tvReleaseYear.setText(movieData.getReleaseDate().substring(0, 4));

        TextView tvReleaseDate = findViewById(R.id.release_date);
        tvReleaseDate.setText(movieData.getReleaseDate());

        TextView tvGenres = findViewById(R.id.genre);
        StringBuilder s = new StringBuilder();

        Iterator<Integer> i = movieData.getGenreIds().listIterator();
        while (i.hasNext()) {

            int genreId = i.next();
            String genreStr = DataUtilities.genre(genreId);
            if (genreStr != null) {
                String capitalized = genreStr.substring(0, 1).toUpperCase() + genreStr.substring(1);
                s.append(capitalized);
            }
            if (i.hasNext())
                s.append(", ");
        }
        tvGenres.setText(s.toString());


        TextView tvUserRating = findViewById(R.id.user_rating);
        tvUserRating.setText(String.valueOf(movieData.getVoteAverage()));
        GradientDrawable bg = (GradientDrawable) tvUserRating.getBackground();
        bg.setColor(
                DataUtilities.getColorForUserRating(movieData.getVoteAverage(), getApplicationContext())
        );

        TextView tvUserVotes = findViewById(R.id.user_votes);
        tvUserVotes.setText(String.valueOf(movieData.getVoteCount()));

        TextView tvPlot = findViewById(R.id.movie_plot);
        tvPlot.setText(movieData.getOverview());
    }

    private void loadMovieTrailers(int movieId) {

        Call<TrailerPage> res = RestUtils.getTMDbInstance().getTrailers(Integer.toString(movieId), RestUtils.dbUserKey);

        res.enqueue(new Callback<TrailerPage>() {
            @Override
            public void onResponse(Call<TrailerPage> call, Response<TrailerPage> response) {
                if (response.isSuccessful()) {
                    if (response.body()!= null){
                        movieTrailers.addAll(response.body().getTrailers());
                        populateMovieTrailers();
                    }
                }
            }

            @Override
            public void onFailure(Call<TrailerPage> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void loadMovieReviews(int movieId) {

        int pgNum = 1;

        Call<ReviewPage> res = RestUtils.getTMDbInstance().getReviews(Integer.toString(movieId)
                , RestUtils.dbUserKey
                , Integer.toString(pgNum));

        res.enqueue(new Callback<ReviewPage>() {
            @Override
            public void onResponse(Call<ReviewPage> call, Response<ReviewPage> response) {
                if (response.isSuccessful()) {
                    if (response.body()!= null){
                        movieReviews.addAll(response.body().getReviews());
                        populateMovieReviews();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewPage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void populateMovieTrailers() {

        LinearLayout containerLayout = findViewById(R.id.row4);

        Iterator<Trailer> i = movieTrailers.iterator();
        while (i.hasNext()) {

            Trailer t = i.next();

            View trailer_layout = LayoutInflater.from(this).inflate(R.layout.trailer_details, containerLayout, false);

            TextView tv_trailer_name = trailer_layout.findViewById(R.id.trailer_name);
            TextView tv_trailer_type = trailer_layout.findViewById(R.id.trailer_type);

            ImageButton btn_trailer_play = trailer_layout.findViewById(R.id.btn_play_trailer);

            tv_trailer_name.setText(t.getName());
            tv_trailer_type.setText(t.getType());

            final String movieKey = t.getKey();

            btn_trailer_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForTrailer(movieKey);
                }
            });

            containerLayout.addView(trailer_layout);

        }

    }

    private void populateMovieReviews() {
        LinearLayout containerLayout = findViewById(R.id.row5);

        Iterator<Review> i = movieReviews.iterator();

        int cnt = 0;
        while (i.hasNext()) {

            Review r = i.next();

            View review_layout = LayoutInflater.from(this).inflate(R.layout.review_details, containerLayout, false);

            TextView tv_review_author = review_layout.findViewById(R.id.review_author);
            TextView tv_review_content = review_layout.findViewById(R.id.review_content);
            TextView tv_review_url = review_layout.findViewById(R.id.review_url);

            tv_review_author.setText(r.getAuthor());
            tv_review_content.setText(r.getContent());
            tv_review_url.setText(r.getUrl());

            tv_review_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForMovieReview(v);
                }
            });

            containerLayout.addView(review_layout);

            cnt++;
            if (cnt >= 5) break;

        }
    }

    private void startActivityForTrailer(String key) {

        if (key == null || key.equals(""))
            return;

        String url = DataUtilities.YOUTUBE_PATH + key;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startActivityForMovieReview(View view) {
        TextView tv = view.findViewById(R.id.review_url);
        String u = tv.getText().toString();
        Uri uri = Uri.parse(u);

        if (uri == null)
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
