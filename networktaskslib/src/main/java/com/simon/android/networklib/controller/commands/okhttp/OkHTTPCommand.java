package com.simon.android.networklib.controller.commands.okhttp;

import android.text.TextUtils;

import com.simon.android.networklib.controller.commands.NetworkCommandAbs;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.TimeUnit;

/**
 * Created by Simon simonkarmy2004@gmail.com on 12/6/2015
 * Network Command Implementation that implements the network layer using OkHTTP library
 */
public abstract class OkHTTPCommand extends NetworkCommandAbs {

    private OkHttpClient client;
    private HttpUrl.Builder urlBuilder;
    private Request.Builder requestBuilder;
    private NetworkInfoProvider networkInfo;

    private boolean canceled = false;

    public OkHTTPCommand() {

        networkInfo = NetworkInfoProvider.getInstance();

        if(TextUtils.isEmpty(networkInfo.getHost()))
            throw new RuntimeException("NetworkInfoProvider not initialized");

        initClient();
        initURL();
        initRequest();
    }

    @Override
    public void cancel() {
        canceled = true;
        if (client != null)
            client.cancel(getCommandTag());
    }

    private void initClient() {

        client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());

        if (networkInfo.getConnectionTimeoutSec() != -1)
            client.setConnectTimeout(networkInfo.getConnectionTimeoutSec(), TimeUnit.SECONDS);

        if (networkInfo.getReadTimeoutSec() != -1)
            client.setReadTimeout(networkInfo.getReadTimeoutSec(), TimeUnit.SECONDS);
    }

    protected void initURL() {

        urlBuilder = new HttpUrl.Builder();

        urlBuilder.host(networkInfo.getHost());
        urlBuilder.scheme(networkInfo.isSecured() ? "https" : "http");

        if (networkInfo.getPort() != -1)
            urlBuilder.port(networkInfo.getPort());

        String[] pathSegments = getPathSegments();
        for (String segment : pathSegments) {

            urlBuilder.addPathSegment(segment);
        }
    }

    private void initRequest() {

        requestBuilder = new Request.Builder();
        requestBuilder.tag(getCommandTag());
    }

    protected Reader send() throws Exception {

        canceled = true;
        requestBuilder.url(urlBuilder.build());
        Request request = requestBuilder.build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().charStream();
        } catch (IOException ex) {
            if(!canceled) {
                ex.printStackTrace();
            }
            throw ex;
        }
    }

    protected void addQueryParameter(String key, String value) {

        urlBuilder.addQueryParameter(key, value);
    }

    protected void addHeader(String name, String value) {

        requestBuilder.addHeader(name, value);
    }

    public void setRequestBody(String requestBody) {

        if(requestBody != null)
            requestBuilder.post(RequestBody.create(MediaType.parse("charset=utf-8"), requestBody));
    }

    private String getCommandTag() {

        return this.getClass().getName();
    }

    protected abstract String[] getPathSegments();
}
