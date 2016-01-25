package com.simon.intigral.movies.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.simon.intigral.movies.R;
import com.simon.intigral.movies.controller.MoviesManager;
import com.simon.intigral.movies.model.MovieDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Simon simon@taqniatech.com on 1/25/2016
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private ArrayList<MovieDetails> moviesList;
    private MovieClickListener mListener;

    public MovieRecyclerAdapter(ArrayList<MovieDetails> moviesList) {
        this.moviesList = moviesList;
    }

    public void setMovieListener(MovieClickListener mListener) {
        this.mListener = mListener;
    }

    public void appendMovies(MovieDetails[] movies) {

        this.moviesList.addAll(Arrays.asList(movies));
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView moviePoster;
        public ProgressBar movieLoading;
        public Context context;
        public MovieViewHolder(View cellView) {
            super(cellView);
            moviePoster = (ImageView) cellView.findViewById(R.id.movie_image);
            movieLoading = (ProgressBar) cellView.findViewById(R.id.movie_loading);
            context = cellView.getContext();
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cell, parent, false);
        final MovieViewHolder vh = new MovieViewHolder(v);
        v.setClickable(true);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.onMovieClicked(moviesList.get(vh.getAdapterPosition()));
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {

        MovieDetails currentMovie = moviesList.get(position);
        holder.movieLoading.setVisibility(View.VISIBLE);
        Picasso.with(holder.context)
                .load(MoviesManager.generatePosterImageURL(currentMovie.getPosterImagePath()))
                .error(R.drawable.movie_error)
                .into(holder.moviePoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.movieLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.movieLoading.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public interface MovieClickListener {

        public void onMovieClicked(MovieDetails movieDetails);
    }
}
