package com.apple.initbmob;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.bean.Note;

import java.util.List;

import cn.bmob.v3.Bmob;


/**
 * Created by limeng on 2017/7/21.
 */

public class InitBmob extends Application {
    private static Context context;
    private static List<Note> list;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        SDKInitializer.initialize(getApplicationContext());
        Bmob.initialize(getApplicationContext(),"fee0209c74bf24304798d10e746fd280");
    }

    public static Context getContext(){
        return context;
    }

    public static List<Note> getList() {
        return list;
    }

    public static void setList(List<Note> list) {
        InitBmob.list = list;
    }
}
