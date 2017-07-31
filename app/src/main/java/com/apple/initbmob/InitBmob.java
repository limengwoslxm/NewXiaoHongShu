package com.apple.initbmob;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;


/**
 * Created by limeng on 2017/7/21.
 */

public class InitBmob extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Bmob.initialize(getApplicationContext(),"fee0209c74bf24304798d10e746fd280");
    }

    public static Context getContext(){
        return context;
    }
}
