package com.simon.intigral.movies.model;

/**
 * Created by Simon simon@taqniatech.com on 1/24/2016
 */
public class MoviesListResponse {

    private int page;
    private MovieDetails[] results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MovieDetails[] getResults() {
        return results;
    }

    public void setResults(MovieDetails[] results) {
        this.results = results;
    }
}
