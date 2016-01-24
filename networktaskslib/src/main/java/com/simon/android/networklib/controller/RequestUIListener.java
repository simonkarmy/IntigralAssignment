package com.simon.android.networklib.controller;

/**
 * Created by Simon on 10/28/2015
 */
public interface RequestUIListener<ResponseType> {

    public void requestWillStart();

    public void onCompleted(ResponseType response, ServerError errorType);
}
