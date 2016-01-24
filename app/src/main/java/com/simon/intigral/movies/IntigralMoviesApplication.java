package com.simon.intigral.movies;

import android.app.Application;

import com.simon.android.networklib.controller.NetworkLib;
import com.simon.intigral.movies.controller.Controller;

/**
 * Created by Simon simon@taqniatech.com on 1/24/2016
 */
public class IntigralMoviesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //initialize the network library
        NetworkLib.init(getApplicationContext());

        //TO init all managers
        Controller.getInstance();
    }
}
