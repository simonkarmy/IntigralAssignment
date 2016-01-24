package com.simon.intigral.movies.controller;

import com.simon.android.networklib.controller.RequestUIListener;
import com.simon.android.networklib.controller.ServerError;
import com.simon.android.networklib.controller.tasks.NetworkTask;
import com.simon.intigral.movies.model.MovieDetails;
import com.simon.intigral.movies.model.MoviesRequestParams;

/**
 * Created by Simon simon@taqniatech.com on 1/24/2016
 *
 * The main class the responsible to maintain any thing related to movies
 * For now it only  has one function to load the movies list for each category
 * May be later it can has : RateMovie(); SubmitReview();.... and so on
 */
public class MoviesManager {

    //Parameters object ot persist the current page to add background loading to preload next page
    private MoviesRequestParams popularParams;
    private MoviesRequestParams topRatedParams;
    private MoviesRequestParams mostRecentParams;
    private MoviesRequestParams revenueParams;

    public MoviesManager() {

        popularParams = new MoviesRequestParams(AppConstants.SORT_BY_POPULARITY_DES);
        topRatedParams = new MoviesRequestParams(AppConstants.SORT_BY_VOTE_AVG_DES);
        mostRecentParams = new MoviesRequestParams(AppConstants.SORT_BY_RELEASE_DES);
        revenueParams = new MoviesRequestParams(AppConstants.SORT_BY_REVENUE_DES);
    }

    public void loadPopularNextPage(final RequestUIListener<MovieDetails[]> uiListener) {

        //increase the page on the request param to load the next page
        popularParams.increasePage();
        loadMoviesForList(popularParams, uiListener);
    }

    public void loadTopRatedNextPage(final RequestUIListener<MovieDetails[]> uiListener) {

        //increase the page on the request param to load the next page
        topRatedParams.increasePage();
        loadMoviesForList(topRatedParams, uiListener);
    }

    public void loadMostRecentNextPage(final RequestUIListener<MovieDetails[]> uiListener) {

        //increase the page on the request param to load the next page
        mostRecentParams.increasePage();
        loadMoviesForList(mostRecentParams, uiListener);
    }

    public void loadRevenueNextPage(final RequestUIListener<MovieDetails[]> uiListener) {

        //increase the page on the request param to load the next page
        revenueParams.increasePage();
        loadMoviesForList(revenueParams, uiListener);
    }

    /**
     * Method create a load movies task for a specific parameters (Movies List)
     * @param requestParams
     * @param uiListener
     */
    private void loadMoviesForList(final MoviesRequestParams requestParams, final RequestUIListener<MovieDetails[]> uiListener) {

        LoadMoviesTask moviesTask = new LoadMoviesTask();
        moviesTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<MovieDetails[]>() {
            @Override
            public void onComplete(MovieDetails[] movieDetails) {
                uiListener.onCompleted(movieDetails, null);
            }
        });

        moviesTask.setOnExceptionListener(new NetworkTask.OnExceptionListener() {
            @Override
            public void onException(Exception exception) {

                //decrease the page count because the current request was failed
                requestParams.decreasePage();
                notifyError(exception, uiListener);
            }
        });

        //notify the UI screen to start loading
        uiListener.requestWillStart();
        moviesTask.execute(requestParams);
    }

    private void notifyError(Exception exception, RequestUIListener<MovieDetails[]> uiListener) {

        ServerError serverError;
        if(exception instanceof ServerError) {

            serverError = (ServerError) exception;
        } else {
            serverError = new ServerError();
            serverError.setErrorCode(ServerError.detectErrorFromException(exception));
            serverError.initCause(exception);
        }
        uiListener.onCompleted(null, serverError);
    }
}
