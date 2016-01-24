package com.simon.android.networklib.controller;

import android.accounts.NetworkErrorException;

import org.json.JSONException;

/**
 * Created by Simon simonkarmy2004@gmail.com on 11/4/2015
 */
public class ServerError {

    public static final String NETWORK_ERROR_CODE = "NL_1000";
    public static final String PARSING_ERROR_CODE = "NL_1001";
    public static final String GENERIC_ERROR_CODE = "NL_1002";

    private String errorCode;
    private Exception cause;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Exception getCause() {
        return cause;
    }

    public void setCause(Exception cause) {
        this.cause = cause;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static String detectErrorFromException(Exception e) {

        if(e instanceof NetworkErrorException)
            return NETWORK_ERROR_CODE;
        else if(e instanceof JSONException)
            return PARSING_ERROR_CODE;
        else
            return GENERIC_ERROR_CODE;
    }
}
