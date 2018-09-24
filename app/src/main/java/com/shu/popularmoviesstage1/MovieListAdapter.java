package com.shu.popularmoviesstage1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private static final String TAG = "MovieListAdapter";
    final String MOVIE_DB_PATH = "https://image.tmdb.org/t/p/w185";

    // members
    private List<MovieData> movieDataList;

    public MovieListAdapter(List<MovieData> data) {
        this.movieDataList = data;
        Log.d(TAG, "Count" + movieDataList.size());
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.movie_list_item, parent, false);
        MovieViewHolder holder = new MovieViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: ");
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return movieDataList.size();
    }

    public class MovieViewHolder extends ViewHolder {

        ImageView iv;

        public MovieViewHolder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView;
        }

        public void bind(int position){

            String path = MOVIE_DB_PATH + movieDataList.get(position).getPoste_path();
            Log.d(TAG, "bind: path is " + path);
            Picasso.with(iv.getContext())
                    .load(path).fit()
                    .centerInside()
                    .into(iv);
        }


    }
}
