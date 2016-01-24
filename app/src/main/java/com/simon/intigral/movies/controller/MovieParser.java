package com.simon.intigral.movies.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simon.intigral.movies.model.MovieDetails;
import com.simon.intigral.movies.model.MoviesListResponse;

import org.json.JSONException;

import java.io.Reader;

/**
 * Created by Simon simon@taqniatech.com on 1/24/2016
 */
public class MovieParser {

    public static MovieDetails[] parseMovieDetails(Reader objectString) throws Exception {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        MoviesListResponse response = gson.fromJson(objectString, MoviesListResponse.class);
        return response.getResults();
    }
}
