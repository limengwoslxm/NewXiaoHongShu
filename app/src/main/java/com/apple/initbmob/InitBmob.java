package com.apple.initbmob;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import cn.bmob.v3.Bmob;


/**
 * Created by limeng on 2017/7/21.
 */

public class InitBmob extends Application {
    private static RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(),"fee0209c74bf24304798d10e746fd280");
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
    }
    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }

}
