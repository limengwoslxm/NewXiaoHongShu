package com.apple.initbmob;

import android.app.Application;

import cn.bmob.v3.Bmob;


/**
 * Created by limeng on 2017/7/21.
 */

public class InitBmob extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(),"fee0209c74bf24304798d10e746fd280");
    }
}
