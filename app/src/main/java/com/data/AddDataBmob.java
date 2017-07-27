package com.data;

import android.util.Log;

import com.bean.MyUser;
import com.bean.Note;
import com.bean.Style;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by xiong on 2017/7/24.
 */

public class AddDataBmob {
    private static MyUser user = BmobUser.getCurrentUser(MyUser.class);
    //添加一个笔记
    public static void addDataToNote(String title,String content,final String[] image,final List<String> styles){
        final Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setAuthor(user);
        note.setUp(0);
        List<BmobFile> imageList = new ArrayList<>();
        for (String img:image) {
            BmobFile bmobFile = new BmobFile(new File(img));
            imageList.add(bmobFile);
        }
        note.setImage(imageList);
        BmobFile.uploadBatch(image, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> urls) {
                if(urls.size()==image.length){
                    note.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                for (String s:styles ) {
                                    String style = SelectDataBmob.getStyleId(s);
                                    noteToStyle(style,objectId);
                                }
                            }else{
                                Log.i("bmob","添加失败："+e.getMessage()+","+e.getErrorCode());
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
                    Log.i("bmob","失败："+e.getMessage());
                }
            }
        });
    }

    //添加多对多关系(关注）
    public static void addAttention(final String myId, final String otherId){
        MyUser my = new MyUser();
        MyUser other = new MyUser();
        my.setObjectId(myId);
        other.setObjectId(otherId);
        BmobRelation relation = new BmobRelation();
        relation.add(other);
        my.setAttention(relation);
        my.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    addFans(otherId,myId);
                    Log.i("bmob","关注成功");
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }
        });
    }

    //添加多对多关系(粉丝）
    public static void addFans(String myId,String otherId){
        MyUser my = new MyUser();
        MyUser other = new MyUser();
        my.setObjectId(myId);
        other.setObjectId(otherId);
        BmobRelation relation = new BmobRelation();
        relation.add(other);
        my.setFans(relation);
        my.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","关注成功");
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }
        });
    }
}
