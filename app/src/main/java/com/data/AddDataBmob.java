package com.data;

import android.util.Log;

import com.bean.MyUser;
import com.bean.Note;
import com.bean.Style;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xiong on 2017/7/24.
 */

public class AddDataBmob {
    private static MyUser user = BmobUser.getCurrentUser(MyUser.class);
    //添加一个笔记
    public static void addDataToNote(Note note, final List<String> styles){
        note.setAuthor(user);
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
    //添加多对多关系
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
}
