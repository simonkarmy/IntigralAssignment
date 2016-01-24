package com.simon.android.networklib.controller.commands.okhttp;

import android.util.Log;

import com.simon.android.networklib.controller.NetworkConstants;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Simon simonkarmy2004@gmail.com on 12/6/2015
 */
public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d(NetworkConstants.TAG, " ====================== New Request");
        Log.d(NetworkConstants.TAG, String.format("%s %n%s", request.url(), request.headers()));

        if(request.body() != null)
            Log.d(NetworkConstants.TAG, String.format("Body: %s",request.body().toString()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d(NetworkConstants.TAG, String.format("Received response for %s in %.1fms",
                response.request().url(), (t2 - t1) / 1e6d));

        return response;
    }
}
