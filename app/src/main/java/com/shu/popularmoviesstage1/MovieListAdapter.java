package com.shu.popularmoviesstage1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    // members
    private List<MovieData> movieDataList;

    public MovieListAdapter(List<MovieData> data) {
        this.movieDataList = data;
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


    }

    @Override
    public int getItemCount() {
        return movieDataList.size();
    }

    public class MovieViewHolder extends ViewHolder {
        ImageView iv_temp;

        public MovieViewHolder(View itemView) {
            super(itemView);

            iv_temp = (ImageView) itemView;
        }
    }
}
