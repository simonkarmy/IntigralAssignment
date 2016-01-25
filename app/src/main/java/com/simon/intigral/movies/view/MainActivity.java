package com.simon.intigral.movies.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.simon.intigral.movies.R;

public class MainActivity extends AppCompatActivity {

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
}
