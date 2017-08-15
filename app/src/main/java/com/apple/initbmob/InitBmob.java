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
    private static int guanzhu,fans,fabu,shoucang;

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

    public static int getGuanzhu() {
        return guanzhu;
    }

    public static void setGuanzhu(int guanzhu) {
        InitBmob.guanzhu = guanzhu;
    }

    public static int getFans() {
        return fans;
    }

    public static void setFans(int fans) {
        InitBmob.fans = fans;
    }

    public static int getFabu() {
        return fabu;
    }

    public static void setFabu(int fabu) {
        InitBmob.fabu = fabu;
    }

    public static int getShoucang() {
        return shoucang;
    }

    public static void setShoucang(int shoucang) {
        InitBmob.shoucang = shoucang;
    }
}
