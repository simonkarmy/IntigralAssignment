package com.simon.intigral.movies.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
public class MoviesListFragment extends Fragment implements RequestUIListener<MovieDetails[]>
        , MovieRecyclerAdapter.MovieClickListener {

    private MoviesManager moviesManager;

    private OnMovieSelectedListener mCallback;

    private ProgressBar popularLoadingView;
    private RecyclerView popularRecycler;
    private MovieRecyclerAdapter popularAdapter;
    private LinearLayoutManager popularLayoutManager;
    private TextView popularLoadingError;
    private boolean popularLoading;

    public MoviesListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesManager = Controller.getInstance().getMoviesManager();
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

        initPopularMoviesList(rootView);

        return rootView;
    }

    private void initPopularMoviesList(View rootView) {

        popularRecycler = (RecyclerView) rootView.findViewById(R.id.popular_movies_recycler_view);
        popularLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRecycler.setLayoutManager(popularLayoutManager);
        popularLoadingView = (ProgressBar) rootView.findViewById(R.id.popular_loading_view);
        popularLoadingError = (TextView) rootView.findViewById(R.id.popular_loading_error);

        if (popularAdapter == null) {
            popularAdapter = new MovieRecyclerAdapter(new ArrayList<MovieDetails>());
            popularAdapter.setMovieListener(this);
            moviesManager.loadPopularNextPage(this);
        }
        popularRecycler.setAdapter(popularAdapter);

        popularRecycler.addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (popularLayoutManager.findLastVisibleItemPosition() > popularAdapter.getItemCount() - 5 && !popularLoading) {
                    popularLoading = true;
                    moviesManager.loadPopularNextPage(MoviesListFragment.this);
                }
            }
        });
    }

    @Override
    public void requestWillStart(TaskRequestID requestID) {

        if (requestID == MoviesRequestID.LOAD_POPULAR) {
            popularLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCompleted(TaskRequestID requestID, MovieDetails[] response, ServerError errorType) {

        if (requestID == MoviesRequestID.LOAD_POPULAR) {
            handlePopularLoadingFinished(response, errorType);
        }
    }

    private void handlePopularLoadingFinished(MovieDetails[] response, ServerError errorType) {

        popularLoading = false;
        popularLoadingView.setVisibility(View.GONE);
        if(errorType == null) {
            popularLoadingError.setVisibility(View.GONE);
            popularRecycler.setVisibility(View.VISIBLE);
            popularAdapter.appendMovies(response);
            popularAdapter.notifyDataSetChanged();
        } else {

            popularLoadingError.setVisibility(View.VISIBLE);
            popularRecycler.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onMovieClicked(MovieDetails movieDetails) {

        if (mCallback != null) {
            mCallback.onMovieSelected(movieDetails);
        }
    }

    public interface OnMovieSelectedListener {
        public void onMovieSelected(MovieDetails movieDetails);
    }
}
