package com.turastory.security;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by tura on 2018-05-04.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
