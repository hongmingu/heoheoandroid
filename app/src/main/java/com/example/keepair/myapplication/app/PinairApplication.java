package com.example.keepair.myapplication.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Keepair on 2016-09-17.
 */
public class PinairApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        PinairApplication.context = getApplicationContext();


    }

    public static Context getAppContext() {
        return PinairApplication.context;
    }
}
