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
            case 204: return "邮箱不能为空";
            case 205: return "该用户不存在";
            case 150: return "文件上传错误";
            case 151: return "文件删除错误";
            case 206: return "缺少用户权限";
            case 210: return "旧密码不正确";
            case 9003: return "上传文件出错";
            case 9004: return "上传文件失败";
            case 9007: return "文件大小超过10M";
            case 9008: return "上传文件不存在";
            case 9009: return "没有缓存数据";
            case 9010: return "网络超时";
            case 9011: return "用户表不支持批量操作";
            case 9016: return "无网络连接，请检查您的手机网络";
            case 9019: return "输入格式不正确";
            case 1003: return "数据已达上限";
            case 1005: return "API请求次数已达上限";
            case 1500: return "上传文件大小超出限制";
            default:
                Log.i("bmob",e.getMessage());
                return "未知错误" + code;
        }
    }
}
