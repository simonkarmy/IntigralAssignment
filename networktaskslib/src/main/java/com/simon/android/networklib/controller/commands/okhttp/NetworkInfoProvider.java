package com.simon.android.networklib.controller.commands.okhttp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Simon simonkarmy2004@gmail.com on 12/6/2015
 * <p/>
 * Singleton class that will hold the network info like hostname, port, is secured or not, and so on
 * It has a method to init its data from a JSON data
 * This data should be added from Assets
 */
public class NetworkInfoProvider {

    private static NetworkInfoProvider instance;

    private String host;
    private int port;
    private boolean isSecured;
    private int connectionTimeoutSec;
    private int readTimeoutSec;

    private NetworkInfoProvider() {
        port = -1;
        isSecured = false;
        readTimeoutSec = -1;
        connectionTimeoutSec = -1;
    }

    public static NetworkInfoProvider getInstance() {
        if (instance == null)
            instance = new NetworkInfoProvider();

        return instance;
    }

    public void initWithData(String host, int port, boolean isSecured, int connectionTimeoutSec, int readTimeoutSec) {
        this.host = host;
        this.port = port;
        this.isSecured = isSecured;
        this.connectionTimeoutSec = connectionTimeoutSec;
        this.readTimeoutSec = readTimeoutSec;
    }

    public void initWithDefaults(String host) {

        this.host = host;
        this.port = -1;
        this.isSecured = false;
        this.connectionTimeoutSec = -1;
        this.readTimeoutSec = -1;
    }

    public void initFromJSON(JSONObject infoJSON) throws JSONException {

        host                 = infoJSON.getString("host");
        port                 = infoJSON.getInt("port");
        isSecured            = infoJSON.getBoolean("isSecured");
        readTimeoutSec       = infoJSON.getInt("readTimeoutSec");
        connectionTimeoutSec = infoJSON.getInt("connectionTimeoutSec");
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isSecured() {
        return isSecured;
    }

    public int getConnectionTimeoutSec() {
        return connectionTimeoutSec;
    }

    public int getReadTimeoutSec() {
        return readTimeoutSec;
    }
}
