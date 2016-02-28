package com.simon.android.networklib.controller.utils;

import android.content.Context;

import com.simon.android.networklib.controller.NetworkLib;

/**
 * Created by Simon simonkarmy2004@gmail.com on 12/10/2015
 */
public class LangUtils {

    public static boolean isAr() {

        return NetworkLib.getContext().getResources().getConfiguration().locale.getLanguage().equals("ar");
    }
}
