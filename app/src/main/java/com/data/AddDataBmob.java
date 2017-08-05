package com.data;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.apple.initbmob.InitBmob;
import com.apple.xhs.five_fragment.home_activity.HomeOpenCamera;
import com.bean.Comment;
import com.bean.Hot;
import com.bean.MyUser;
import com.bean.Note;
import com.bean.Style;
import com.collecter.ErrorCollecter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.FindListener;
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
    public static void addDataToNote(final String title, final String content, final List<String> image, final List<String> styles){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Note note = new Note();
                note.setTitle(title);
                note.setContent(content);
                note.setAuthor(user);
                note.setUp(0);
                String[] imglist = new String[image.size()];
                final List<BmobFile> imageList = new ArrayList<>();
                for (int i = 0;i < image.size();i++) {
                    String img = image.get(i);
                    String tempPath = Environment.getExternalStorageDirectory().getPath()
                            + "/XHS/temp/" + System.currentTimeMillis() + ".jpg";
                    compressBitmap(img,tempPath);
                    Log.i("bmob","图片压缩成功，数目：" + (i+1) + "，地址：" + tempPath);
                    imglist[i] = tempPath;
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
                                        Log.i("bmob","笔记添加成功:" + note.getTitle());
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
                        }else {
                            Log.i("bmob","等待上传图片数目：" + (image.size()-urls.size()));
                        }
                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {

                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.i("bmob","图片上传失败：<" + i + ">" + s);
                    }
                });
            }
        }).start();
    }
    //图片压缩
    public static void compressBitmap(String imgPath, String outPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
        try {
            File dir = new File( Environment.getExternalStorageDirectory().getPath()
                    + "/XHS/temp/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream os = new FileOutputStream(outPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //添加评论
    public static void addComment(final Note note, String data){
        final MyUser user = BmobUser.getCurrentUser(MyUser.class);
        final Comment comment = new Comment();
        comment.setContent(data);
        comment.setNote(note);
        comment.setUser(user);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    Log.i("bmob","评论添加成功：" + "用户<" + user.getNickname() + ">对笔记<" + note.getTitle() + ">评论：" + comment.getContent());
                }else {
                    Toast.makeText(InitBmob.getContext(),ErrorCollecter.errorCode(e),Toast.LENGTH_SHORT).show();
                    Log.i("bmob","评论添加失败：" + e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //添加多对多关系(note--style）
    public static void noteToStyle(String styleId,String noteId){
        final Note note = new Note();
        note.setObjectId(noteId);
        final Style style = new Style();
        style.setObjectId(styleId);
        BmobRelation relation = new BmobRelation();
        relation.add(note);
        style.setNote(relation);
        style.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","标签绑定成功：" + note.getTitle() + "---->" + style.getName());
                }else{
                    Log.i("bmob","标签绑定失败："+e.getMessage() + e.getErrorCode());
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
                    Log.i("bmob","执行云端关注方法成功，返回：" + o.toString());
                }else {
                    Toast.makeText(InitBmob.getContext(),ErrorCollecter.errorCode(e),Toast.LENGTH_SHORT).show();
                    Log.i("bmob","执行云端关注方法失败：" + e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //添加收藏
    public static void addLikes(final Note note){
        final MyUser my = BmobUser.getCurrentUser(MyUser.class);
        BmobRelation relation = new BmobRelation();
        relation.add(note);
        my.setLikes(relation);
        my.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(InitBmob.getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                    Log.i("bmob","收藏成功：" + "用户<" + my.getNickname() + ">收藏了笔记<" + note.getTitle() + ">");
                }else{
                    Toast.makeText(InitBmob.getContext(),ErrorCollecter.errorCode(e),Toast.LENGTH_SHORT).show();
                    Log.i("bmob","收藏失败："+e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //添加历史搜索
    public static void addHistory(String ss){
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        List<String> list = user.getHistory();
        for (String s : list){
            if (s.equals(ss)){
                DeleteDataBmob.deleteHisOne(s);
            }
        }
        user.addUnique("history",ss);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    Log.i("bmob","历史搜索保存成功");
                }else{
                    Log.i("bmob","历史搜索保存失败："+e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    //添加热门搜索
    public static void addHot(final String ss){
        BmobQuery<Hot> query = new BmobQuery<Hot>();
        query.addWhereEqualTo("name",ss);
        query.findObjects(new FindListener<Hot>() {
            @Override
            public void done(List<Hot> list, BmobException e) {
                if (e==null){
                    if (list.size()==0){
                        Hot hot = new Hot();
                        hot.setName(ss);
                        hot.setNumber(0);
                        hot.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e==null){
                                    Log.i("bmob","热门搜索添加成功");
                                }else {
                                    Log.i("bmob","热门搜索添加失败" + e.getMessage() + e.getErrorCode());
                                }
                            }
                        });
                    }else {
                        Hot hot = list.get(0);
                        hot.increment("number",1);
                        hot.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Log.i("bmob","热门搜索更新成功");
                                }else {
                                    Log.i("bmob","热门搜索更新失败"  + e.getMessage() + e.getErrorCode());
                                }
                            }
                        });
                    }
                }else {
                    Log.i("bmob","热门搜索查询失败" + e.getMessage() + e.getErrorCode());
                }
            }
        });
    }
}
