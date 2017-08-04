package com.data;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.apple.initbmob.InitBmob;
import com.bean.Comment;
import com.bean.MyUser;
import com.bean.Note;
import com.bean.Style;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by xiong on 2017/7/24.
 */

public class SelectDataBmob {

    //在Style表中查找styleName对应notes
    public static void getNoteByStyle(String styleName){
        BmobQuery<Note> query = new BmobQuery<Note>();
        Style style = new Style();
        style.setObjectId(getStyleId(styleName));
        query.addWhereRelatedTo("note",new BmobPointer(style));
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e==null){

                }else{
                    Log.i("bmob",e + "");
                }
            }
        });
    }

    //获取本人的笔记
    public static void getMineNote(){
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.addWhereEqualTo("author",myUser);
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e==null){
                    Log.i("bmob","成功");
                }else{
                    Log.i("bmob","获取本人笔记失败："+e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //关注列表
    public void selectAttentions(){
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereRelatedTo("attention",new BmobPointer(myUser));
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e==null){

                }else {
                    Log.i("bmob","获取关注列表失败："+e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //粉丝列表
    public void selectFans(){
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereRelatedTo("fans",new BmobPointer(myUser));
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e==null){

                }else {
                    Log.i("bmob","获取粉丝列表失败："+e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //我的收藏
    public void selectLikes(){
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.addWhereRelatedTo("likes",new BmobPointer(myUser));
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e==null){

                }else {
                    Log.i("bmob","获取收藏列表失败："+e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //模糊查询
    public void selectMore(final String ss){
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e==null){
                    List<Note> newList = new ArrayList<>();
                    for (Note note:list){
                        if (note.getTitle().contains(ss)){
                            newList.add(note);
                        }
                    }
                    if (newList.size()==0){
                        Toast.makeText(InitBmob.getContext(),"结果不存在",Toast.LENGTH_SHORT).show();
                    }else {
                        //代码块
                    }
                }else {
                    Log.i("bmob","模糊查询失败" + e.getErrorCode() + e.getMessage());
                }
            }
        });
    }

    //查询评论-第一页
    public void selectComment(Note note){
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.addWhereEqualTo("note",note);
        query.order("-createdAt");
        query.include("user");
        query.setLimit(3);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e==null){
                    Toast.makeText(InitBmob.getContext(),"查询到" + list.size() + "条评论",Toast.LENGTH_SHORT).show();
                }else {
                    Log.i("bmob","查询评论失败" + e.getErrorCode() + e.getMessage());
                }
            }
        });
    }

    public static String getStyleId(String name){
        switch (name){
            case "武汉": return "Nbze4449";
            case "母婴": return "HEXG999Q";
            case "彩妆": return "Rbun1116";
            case "旅行": return "rAuZFFFs";
            case "运动": return "sdQ1777d";
            case "美食": return "hBaF999C";
            case "时尚": return "byZHwww1";
            case "居家": return "cSvsVVVu";
            case "护肤": return "jeM5sssz";
            case "男人": return "N7cyXXXz";
            default: return null;
        }
    }
}
