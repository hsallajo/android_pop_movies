package com.shu.popularmovies;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shu.popularmovies.model.Movie;
import com.shu.popularmovies.rest.RestUtils;
import com.squareup.picasso.Picasso;
import java.util.List;



public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private final List<Movie> movieData;
    private final MovieListClickListener uiClickListener;


    public MovieListAdapter(List<Movie> data, MovieListClickListener uiClickListener) {

        this.movieData = data;
        this.uiClickListener = uiClickListener;

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView iv;

        private MovieViewHolder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView;
            iv.setOnClickListener(this);
        }

        private void bind(int position) {

            String path = RestUtils.MOVIE_DB_POSTER_PATH + movieData.get(position).getPosterPath();

            Picasso.get()
                    .load(path)
                    .into(iv);
        }

        @Override
        public void onClick(View v) {

            uiClickListener.onMovieListItemClick(getAdapterPosition());

        }
    }

    public interface MovieListClickListener {

        void onMovieListItemClick(int position);

    }
}
