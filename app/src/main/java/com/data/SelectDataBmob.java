package com.data;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bean.Note;
import com.bean.Style;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by xiong on 2017/7/24.
 */

public class SelectDataBmob {

    public static Handler handler;

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
                    Message message = handler.obtainMessage();
                    message.what = 0;
                    message.obj = list;
                    handler.sendMessage(message);
                }else{
                    Log.i("bmob",e + "");
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