package com.simon.android.networklib.controller;

import com.simon.android.networklib.controller.tasks.TaskRequestID;

/**
 * Created by Simon on 10/28/2015
 */
public interface RequestUIListener<ResponseType> {

    public void requestWillStart(TaskRequestID requestID);

    public void onCompleted(TaskRequestID requestID, ResponseType response, ServerError errorType);
}
