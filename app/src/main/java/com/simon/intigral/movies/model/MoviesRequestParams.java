package com.simon.intigral.movies.model;

/**
 * Created by Simon simon@taqniatech.com on 1/24/2016
 */
public class MoviesRequestParams {

    private int page;
    private String sortingKey;

    public MoviesRequestParams(String sortingKey) {
        this.sortingKey = sortingKey;
    }

    public void increasePage() {
        this.page++;
    }

    public void decreasePage() {
        this.page--;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSortingKey() {
        return sortingKey;
    }
}
