package com.simon.intigral.movies.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.simon.android.networklib.controller.RequestUIListener;
import com.simon.android.networklib.controller.ServerError;
import com.simon.intigral.movies.R;
import com.simon.intigral.movies.controller.Controller;
import com.simon.intigral.movies.model.MovieDetails;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Controller.getInstance().getMoviesManager().loadPopularNextPage(new RequestUIListener<MovieDetails[]>() {
            @Override
            public void requestWillStart() {
                findViewById(R.id.loading).setVisibility(View.VISIBLE);
            }

            @Override
            public void onCompleted(MovieDetails[] response, ServerError errorType) {
                findViewById(R.id.loading).setVisibility(View.GONE);

                TextView textView = (TextView) findViewById(R.id.text_view);
                if(errorType != null) {
                    textView.setText(errorType.getErrorMessage());
                } else {
                    textView.setText(response.length + "");
                }
            }
        });
    }
}
