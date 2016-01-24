package com.simon.intigral.movies.model;

import com.simon.android.networklib.controller.tasks.TaskRequestID;

/**
 * Created by Simon on 1/24/2016.
 * A Constant Tag to persist Tags for each request
 */
public enum MoviesRequestID implements TaskRequestID {

    LOAD_POPULAR,
    LOAD_TOP_RATED,
    LOAD_RECENT,
    LOAD_REVENUE;
}
