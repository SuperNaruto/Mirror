package com.example.vdllo.mirror.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Bo on 16/3/29.
 */
public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
//        Fresco.initialize(context);
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }

    public static Context getContext() {
        return context;
    }
}
