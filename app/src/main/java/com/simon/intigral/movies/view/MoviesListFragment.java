package com.simon.intigral.movies.view;


import android.content.Context;
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
public class MoviesListFragment extends Fragment implements RequestUIListener<MovieDetails[]>,MovieRecyclerAdapter.MovieClickListener {

    private MoviesManager moviesManager;
    private MovieRecyclerAdapter popularAdapter;
    private OnMovieSelectedListener mCallback;

    public MoviesListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesManager = Controller.getInstance().getMoviesManager();

        popularAdapter = new MovieRecyclerAdapter(new ArrayList<MovieDetails>());
        popularAdapter.setMovieListener(this);

        moviesManager.loadPopularNextPage(this);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnMovieSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMovieSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies_list, container, false);

        RecyclerView popularRecycler = (RecyclerView) rootView.findViewById(R.id.popular_movies_recycler_view);
        LinearLayoutManager horLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRecycler.setLayoutManager(horLayoutManager);

        popularRecycler.setAdapter(popularAdapter);

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

    @Override
    public void onMovieClicked(MovieDetails movieDetails) {

        if(mCallback != null) {
            mCallback.onMovieSelected(movieDetails);
        }
    }

    public interface OnMovieSelectedListener {
        public void onMovieSelected(MovieDetails movieDetails);
    }
}
