package com.simon.android.networklib.controller;

import android.app.Application;
import android.content.Context;

import com.simon.android.networklib.controller.commands.okhttp.NetworkInfoProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Simon simonkarmy2004@gmail.com on 12/6/2015
 */
public class NetworkLib {

    private static Context appContext;

    public static void init(Context context) {

        appContext = context.getApplicationContext();
        initNetworkInfoFromAssets();
    }

    private static void initNetworkInfoFromAssets() {

        String networkInfoFileContent = readFromFile(NetworkConstants.NETWORK_INFO_ASSETS_FILE, appContext);

        try {
            NetworkInfoProvider.getInstance().initFromJSON(new JSONObject(networkInfoFileContent));
        } catch (JSONException e) {
            e.printStackTrace();
            NetworkInfoProvider.getInstance().initWithDefaults(null);
        }
    }

    public static String readFromFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    public static Context getContext() {
        if (appContext == null)
            throw new IllegalStateException("Network Lib not initialized yet, Call init(context) from you Application Class");
        else
            return appContext;
    }
}