package com.shu.popularmoviesstage1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    // constants
    // private static final String TAG = "MovieListAdapter";

    // members
    private final List<MovieData> movieData;
    private final MovieListClickListener uiClickListener;


    // constructors
    public MovieListAdapter(List<MovieData> data, MovieListClickListener uiClickListener) {
        this.movieData = data;
        this.uiClickListener = uiClickListener;
    }

    // overrides and callbacks
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

    public class MovieViewHolder extends ViewHolder implements View.OnClickListener {

        private final ImageView iv;

        private MovieViewHolder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView;
            iv.setOnClickListener(this);
        }

        private void bind(int position){

            String path = JsonUtilities.MOVIE_DB_POSTER_PATH + movieData.get(position).poster_path;
            //Log.i(TAG, "Image path: " + path);
            Picasso.with(iv.getContext())
                    .load(path).fit()
                    .centerInside()
                    .into(iv);
        }

        @Override
        public void onClick(View v) {
            uiClickListener.onMovieListItemClick(getAdapterPosition());
        }
    }

    // interfaces
    public interface MovieListClickListener{
        void onMovieListItemClick(int position);
    }
}
