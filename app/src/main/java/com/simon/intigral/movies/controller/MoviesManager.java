package com.simon.intigral.movies.controller;

import com.simon.android.networklib.controller.RequestUIListener;
import com.simon.android.networklib.controller.ServerError;
import com.simon.android.networklib.controller.tasks.NetworkTask;
import com.simon.intigral.movies.model.MovieDetails;
import com.simon.intigral.movies.model.MoviesRequestID;
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
        loadMoviesForList(MoviesRequestID.LOAD_POPULAR, popularParams, uiListener);
    }

    public void loadTopRatedNextPage(final RequestUIListener<MovieDetails[]> uiListener) {

        //increase the page on the request param to load the next page
        topRatedParams.increasePage();
        loadMoviesForList(MoviesRequestID.LOAD_TOP_RATED, topRatedParams, uiListener);
    }

    public void loadMostRecentNextPage(final RequestUIListener<MovieDetails[]> uiListener) {

        //increase the page on the request param to load the next page
        mostRecentParams.increasePage();
        loadMoviesForList(MoviesRequestID.LOAD_RECENT, mostRecentParams, uiListener);
    }

    public void loadRevenueNextPage(final RequestUIListener<MovieDetails[]> uiListener) {

        //increase the page on the request param to load the next page
        revenueParams.increasePage();
        loadMoviesForList(MoviesRequestID.LOAD_REVENUE, revenueParams, uiListener);
    }

    /**
     * Method create a load movies task for a specific parameters (Movies List)
     * @param requestID the id for the request
     * @param requestParams
     * @param uiListener
     */
    private void loadMoviesForList(final MoviesRequestID requestID,
                                   final MoviesRequestParams requestParams,
                                   final RequestUIListener<MovieDetails[]> uiListener) {

        LoadMoviesTask moviesTask = new LoadMoviesTask();
        moviesTask.setTaskRequestTag(requestID);
        moviesTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<MovieDetails[]>() {
            @Override
            public void onComplete(MovieDetails[] movieDetails) {
                uiListener.onCompleted(requestID, movieDetails, null);
            }
        });

        moviesTask.setOnExceptionListener(new NetworkTask.OnExceptionListener() {
            @Override
            public void onException(Exception exception) {

                //decrease the page count because the current request was failed
                requestParams.decreasePage();
                notifyError(requestID, exception, uiListener);
            }
        });

        //notify the UI screen to start loading
        uiListener.requestWillStart(requestID);
        moviesTask.execute(requestParams);
    }

    private void notifyError(MoviesRequestID requestID, Exception exception, RequestUIListener<MovieDetails[]> uiListener) {

        ServerError serverError;
        if(exception instanceof ServerError) {

            serverError = (ServerError) exception;
        } else {
            serverError = new ServerError();
            serverError.setErrorCode(ServerError.detectErrorFromException(exception));
            serverError.initCause(exception);
        }
        uiListener.onCompleted(requestID, null, serverError);
    }

    public static String generatePosterImageURL(String imagePath) {

        return "http://image.tmdb.org/t/p/w300" + imagePath;
    }

    public static String generateBannerImageURL(String imagePath) {

        return "http://image.tmdb.org/t/p/w500" + imagePath;
    }
}
