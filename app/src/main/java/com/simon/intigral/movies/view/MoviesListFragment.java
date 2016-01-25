package com.simon.intigral.movies.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simon.android.networklib.controller.RequestUIListener;
import com.simon.android.networklib.controller.ServerError;
import com.simon.android.networklib.controller.tasks.TaskRequestID;
import com.simon.intigral.movies.R;
import com.simon.intigral.movies.controller.Controller;
import com.simon.intigral.movies.controller.MoviesManager;
import com.simon.intigral.movies.model.MovieDetails;
import com.simon.intigral.movies.model.MoviesRequestID;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment implements RequestUIListener<MovieDetails[]> {


    private MoviesManager moviesManager;
    private MovieRecyclerAdapter popularAdapter;

    public MoviesListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies_list, container, false);

        moviesManager = Controller.getInstance().getMoviesManager();

        RecyclerView popularRecycler = (RecyclerView) rootView.findViewById(R.id.popular_movies_recycler_view);
        LinearLayoutManager horLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRecycler.setLayoutManager(horLayoutManager);

        popularAdapter = new MovieRecyclerAdapter(new ArrayList<MovieDetails>());
        popularRecycler.setAdapter(popularAdapter);

        moviesManager.loadPopularNextPage(this);

        return rootView;
    }

    @Override
    public void requestWillStart(TaskRequestID requestID) {

    }

    @Override
    public void onCompleted(TaskRequestID requestID, MovieDetails[] response, ServerError errorType) {

        if(requestID == MoviesRequestID.LOAD_POPULAR) {
            popularAdapter.appendMovies(response);
            popularAdapter.notifyDataSetChanged();
        }
    }
}
