package com.simon.intigral.movies.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.simon.intigral.movies.R;
import com.simon.intigral.movies.model.MovieDetails;

public class MainActivity extends AppCompatActivity implements MoviesListFragment.OnMovieSelectedListener {

    private MoviesListFragment listFragment;
    private MovieDetailsFragment detailsFragment;

    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTablet = findViewById(R.id.screen_content) == null;

        if(isTablet) {

            listFragment = (MoviesListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
            detailsFragment = (MovieDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);
        } else {

            listFragment = new MoviesListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.screen_content, listFragment).commit();
        }

        if(isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onMovieSelected(MovieDetails movieDetails) {

        if(detailsFragment != null) {

            detailsFragment.updateMovieDetails(movieDetails);
        }
        if(!isTablet) {
            detailsFragment = new MovieDetailsFragment();
            Bundle args = new Bundle();
            args.putSerializable(MovieDetailsFragment.MOVIE_DETAILS, movieDetails);
            detailsFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.screen_content, detailsFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }
}
