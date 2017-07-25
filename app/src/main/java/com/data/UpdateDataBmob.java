package com.data;

import android.util.Log;

import com.bean.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by xiong on 2017/7/24.
 */

public class UpdateDataBmob {
    //更新头像
    public static void UpdataHead(final BmobFile bmobFile){
        final MyUser user = BmobUser.getCurrentUser(MyUser.class);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
//                    toast("上传文件成功:" + bmobFile.getFileUrl());
                    MyUser myUser = new MyUser();
                    myUser.setValue("head",bmobFile);
                    myUser.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.i("bmob","头像更新成功");
                            }else{
                                Log.i("bmob","头像更新失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }else{
                    Log.i("bmob","上传失败" + e);
                }
            }
        });
    }

    //更改昵称
    public static void UpdataNickname(String name){
        final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        MyUser user = new MyUser();
        user.setValue("nickname",name);
        user.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","昵称更新成功");
                }else{
                    Log.i("bmob","昵称更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    //更改性别
    public static void UpdataSex(boolean sex){
        final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        MyUser user = new MyUser();
        user.setValue("sex",sex);
        user.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","性别更新成功");
                }else{
                    Log.i("bmob","性别更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    //更改生日
    public static void UpdataBirthday(String birth){
        final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        MyUser user = new MyUser();
        user.setValue("birthday",birth);
        user.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","生日更新成功");
                }else{
                    Log.i("bmob","生日更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    //更改个性签名
    public static void UpdataSignature(String signature){
        final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        MyUser user = new MyUser();
        user.setValue("signature",signature);
        user.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","签名更新成功");
                }else{
                    Log.i("bmob","签名更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
