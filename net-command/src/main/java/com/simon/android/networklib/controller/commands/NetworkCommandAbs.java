package com.simon.android.networklib.controller.commands;

/**
 * Created by Simon simonkarmy2004@gmail.com on 12/6/2015
 */
public abstract class NetworkCommandAbs {

    protected abstract Object execute() throws Exception;
    protected abstract void cancel();
}
