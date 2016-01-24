package com.simon.intigral.movies.controller;

/**
 * Created by Simon simon@taqniatech.com on 1/24/2016
 */
public class Controller {

    private static Controller instance;

    private MoviesManager moviesManager;
    public static Controller getInstance() {
        if(instance == null)
            instance = new Controller();
        return instance;
    }

    private Controller() {

        moviesManager = new MoviesManager();
    }

    public MoviesManager getMoviesManager() {
        return moviesManager;
    }
}
