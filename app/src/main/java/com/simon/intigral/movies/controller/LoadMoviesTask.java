package com.simon.intigral.movies.controller;

import com.simon.android.networklib.controller.tasks.NetworkTask;
import com.simon.intigral.movies.model.MovieDetails;
import com.simon.intigral.movies.model.MoviesRequestParams;

/**
 * Created by Simon simon@taqniatech.com on 1/24/2016
 * A network Task that responsible to load movies list for a specific criteria
 */
public class LoadMoviesTask extends NetworkTask<MoviesRequestParams, Void, MovieDetails[]> {

    private LoadMoviesCommand loadMoviesCommand;

    @Override
    protected MovieDetails[] doNetworkAction(MoviesRequestParams... params) throws Exception {
        loadMoviesCommand = new LoadMoviesCommand(params[0]);
        return loadMoviesCommand.execute();
    }

    @Override
    protected void cancelCommands() {

        loadMoviesCommand.cancel();
    }
}
