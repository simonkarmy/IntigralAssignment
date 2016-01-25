package com.simon.intigral.movies.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simon.intigral.movies.R;
import com.simon.intigral.movies.controller.MoviesManager;
import com.simon.intigral.movies.model.MovieDetails;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {

    public static final String MOVIE_DETAILS = "MOVIE_DETAILS";

    private ImageView movieBannerView;
    private TextView movieNameView;
    private TextView movieRatingView;
    private TextView movieOverviewView;

    public MovieDetailsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        movieBannerView = (ImageView) rootView.findViewById(R.id.movie_details_banner);
        movieNameView = (TextView) rootView.findViewById(R.id.movie_details_name);
        movieRatingView = (TextView) rootView.findViewById(R.id.movie_details_rating);
        movieOverviewView = (TextView) rootView.findViewById(R.id.movie_details_overview);

        if (getArguments() != null) {
            MovieDetails currentMovie = (MovieDetails) getArguments().getSerializable(MOVIE_DETAILS);
            if (currentMovie != null)
                updateMovieDetails(currentMovie);
        }

        return rootView;
    }

    public void updateMovieDetails(MovieDetails movieDetails) {

        movieNameView.setText(movieDetails.getTitle());
        movieRatingView.setText(String.valueOf(movieDetails.getVoteAvg()));
        if (!TextUtils.isEmpty(movieDetails.getOverview())) {
            movieOverviewView.setText(movieDetails.getOverview());
        } else {
            movieOverviewView.setText(R.string.desc_not_available);
        }

        Picasso.with(getContext())
                .load(MoviesManager.generateBannerImageURL(movieDetails.getBannerImagePath()))
                .into(movieBannerView);
    }

}
