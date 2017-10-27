package com.smq.mode2;

import android.app.Application;

import org.xutils.x;


public class Mode2Application extends Application {
    private static Mode2Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        x.Ext.init(this);
    }

    public static Mode2Application getInstance() {
        return instance;
    }
}
