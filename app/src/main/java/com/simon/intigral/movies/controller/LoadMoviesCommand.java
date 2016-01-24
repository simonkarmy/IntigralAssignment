package com.simon.intigral.movies.controller;

import android.graphics.Movie;

import com.simon.android.networklib.controller.commands.okhttp.OkHTTPCommand;
import com.simon.intigral.movies.model.MovieDetails;
import com.simon.intigral.movies.model.MoviesRequestParams;

import java.io.Reader;

/**
 * Created by Simon simon@taqniatech.com on 1/24/2016
 */
public class LoadMoviesCommand extends OkHTTPCommand {

    private MoviesRequestParams requestParams;

    public LoadMoviesCommand(MoviesRequestParams requestParams) {

        this.requestParams = requestParams;
    }

    @Override
    protected MovieDetails[] execute() throws Exception {

        addQueryParameter(AppConstants.SORT_BY_KEY, requestParams.getSortingKey());
        addQueryParameter(AppConstants.PAGE_KEY, String.valueOf(requestParams.getPage()));
        addQueryParameter(AppConstants.API_KEY_TAG, AppConstants.API_KEY_VALUE);
        Reader responseText = send();
        return MovieParser.parseMovieDetails(responseText);
    }

    @Override
    protected String[] getPathSegments() {
        return new String[]{"3", "discover", "movie"};
    }
}
