package com.lovewuchin.memorization;

import android.app.Application;
import android.content.res.Resources;

/**
 * @author Chin
 * @since 2014/11/28
 */
public class MApplication extends Application {

    private static MApplication instance;

    public static MApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
