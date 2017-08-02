package com.data;

import android.util.Log;
import android.widget.Toast;

import com.apple.initbmob.InitBmob;
import com.bean.MyUser;
import com.bean.Note;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xiong on 2017/7/24.
 */

public class DeleteDataBmob {
    //取消收藏
    public static void deleteLikes(Note note){
        MyUser my = BmobUser.getCurrentUser(MyUser.class);
        BmobRelation relation = new BmobRelation();
        relation.remove(note);
        my.setLikes(relation);
        my.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(InitBmob.getContext(),"取消收藏成功",Toast.LENGTH_SHORT).show();
                    Log.i("bmob","取消收藏成功");
                }else{
                    Log.i("bmob","取消收藏失败："+e.getMessage() + e.getErrorCode());
                }
            }
        });
    }
}
