package com.data;

import android.app.Activity;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.apple.initbmob.InitBmob;
import com.bean.Comment;
import com.bean.MyUser;
import com.bean.Note;
import com.bean.Style;
import com.collecter.ErrorCollecter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by xiong on 2017/7/24.
 */

public class AddDataBmob {
    private static MyUser user = BmobUser.getCurrentUser(MyUser.class);
    //添加一个笔记
    public static void addDataToNote(String title,String content,final List<String> image,final List<String> styles){
        final Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setAuthor(user);
        note.setUp(0);
        String[] imglist = new String[image.size()];
        final List<BmobFile> imageList = new ArrayList<>();
        for (int i = 0;i < image.size();i++) {
            String img = image.get(i);
            imglist[i] = img;
        }
        BmobFile.uploadBatch(imglist, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> urls) {
                Log.i("bmob","已上传图片数目：" + urls.size() + " 总数为：" + image.size());
                if(urls.size()==image.size()){
                    imageList.addAll(list);
                    note.setImage(imageList);
                    Log.i("bmob","图片上传成功："+imageList.size());
                    note.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                if (styles.size()!=0){
                                    for (String s:styles ) {
                                        String style = SelectDataBmob.getStyleId(s);
                                        noteToStyle(style,objectId);
                                    }
                                }
                                Toast.makeText(InitBmob.getContext(), "笔记添加成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(InitBmob.getContext(), ErrorCollecter.errorCode(e), Toast.LENGTH_SHORT).show();
                                Log.i("bmob","笔记添加失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    //添加评论
    public static void addComment(Note note,String data){
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        Comment comment = new Comment();
        comment.setContent(data);
        comment.setNote(note);
        comment.setUser(user);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    Toast.makeText(InitBmob.getContext(),"评论添加成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(InitBmob.getContext(),"评论添加失败",Toast.LENGTH_SHORT).show();
                    Log.i("bmob","评论添加失败" + e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //添加多对多关系(note--style）
    public static void noteToStyle(String styleId,String noteId){
        Note note = new Note();
        note.setObjectId(noteId);
        Style style = new Style();
        style.setObjectId(styleId);
        BmobRelation relation = new BmobRelation();
        relation.add(note);
        style.setNote(relation);
        style.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","多对多关联添加成功");
                }else{
                    Log.i("bmob","多对多关联添加失败："+e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //添加多对多关系(关注）
    public static void addAttention(final String otherId){
        final MyUser my = BmobUser.getCurrentUser(MyUser.class);
        String cloudCodeName = "addGuanzhu";
        JSONObject params = new JSONObject();
        try {
            params.put("myId",my.getObjectId());
            params.put("otherId",otherId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
        cloudCode.callEndpoint(cloudCodeName,params, new CloudCodeListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (e==null){
                    Toast.makeText(InitBmob.getContext(),o.toString(),Toast.LENGTH_SHORT).show();
                    Log.i("bmob","关注成功，返回：" + o.toString());
                }else {
                    Toast.makeText(InitBmob.getContext(),ErrorCollecter.errorCode(e),Toast.LENGTH_SHORT).show();
                    Log.i("bmob","关注失败" + e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //添加收藏
    public static void addLikes(Note note){
        MyUser my = BmobUser.getCurrentUser(MyUser.class);
        BmobRelation relation = new BmobRelation();
        relation.add(note);
        my.setLikes(relation);
        my.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(InitBmob.getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                    Log.i("bmob","收藏成功");
                }else{
                    Log.i("bmob","收藏失败："+e.getMessage() + e.getErrorCode());
                }
            }
        });
    }
}
