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
    private static String local_head_url;
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(),"fee0209c74bf24304798d10e746fd280");
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
    }
    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }

    public static String getLocal_head_url() {
        return local_head_url;
    }

    public static void setLocal_head_url(String local_head_url) {
        InitBmob.local_head_url = local_head_url;
    }
}
