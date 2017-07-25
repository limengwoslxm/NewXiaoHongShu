package com.collecter;

import android.util.Log;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by xiong on 2017/7/24.
 */

public class ErrorCollecter {
    public static String errorCode(BmobException e){
        int code = e.getErrorCode();
        switch (code){
            case 101: return "用户名或密码错误";
            case 108: return "用户名和密码不能为空";
            case 109: return "用户名和密码不能为空";
            case 202: return "用户名已存在";
            case 203: return "邮箱已存在";
            case 205: return "该用户不存在";
            default:
                Log.i("bmob",e.getMessage());
                return "未知错误" + code;
        }
    }
}
