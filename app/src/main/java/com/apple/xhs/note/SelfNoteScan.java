package com.apple.xhs.note;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apple.xhs.R;
import com.apple.xhs.ServiceHelp;
import com.apple.xhs.custom_view.InfoSettingTitle;
import com.apple.xhs.custom_view.SelfNoteCard;
import com.base.BaseActivity;
import com.bean.MyUser;
import com.bean.Note;
import com.data.DeleteDataBmob;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by limeng on 2017/7/31.
 */

public class SelfNoteScan extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.selfnotetoolbar)
    InfoSettingTitle toolbar;
    @BindView(R.id.note_card_parent)
    LinearLayout parent;
    MyUser user;
    @Override
    public int getContentViewId() {
        return R.layout.note_selfnote;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        addListener();
    }

    private void addListener() {
        toolbar.setDoneListener(this);
        toolbar.setImgListener(this);
    }

    private void initData() {
        user = (MyUser) getIntent().getSerializableExtra("userselfnote");
        if(user.getObjectId().equals(BmobUser.getCurrentUser(MyUser.class).getObjectId())){
            toolbar.setTitleText("我的笔记");
        }else {
            toolbar.setTitleText("TA的笔记");
        }
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.addWhereEqualTo("author",user);
        query.include("author");
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e==null){
                    Log.i("bmob","获取笔记成功");
                    addNoteCard(list);
                }else{
                    Log.i("bmob","获取笔记失败："+e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    private void addNoteCard(final List<Note> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i = list.size()-1 ; i >= 0 ; i--){
                    final Note note = list.get(i);
                    SelfNoteCard card = new SelfNoteCard(getApplicationContext());
                    card.setSelfNoteTitle(note.getTitle());
                    card.setSelfNoteShoucang("已被收藏"+note.getUp()+"次");
                    card.setSelfNoteDate(note.getUpdatedAt());
                    if(note.getImage().size()==1){
                        card.setSketchImageOne(note.getImage().get(0).getUrl());
                    }else if(note.getImage().size()==2){
                        card.setSketchImageOne(note.getImage().get(0).getUrl());
                        card.setSketchImageTwo(note.getImage().get(1).getUrl());
                    }else if(note.getImage().size()==3){
                        card.setSketchImageOne(note.getImage().get(0).getUrl());
                        card.setSketchImageTwo(note.getImage().get(1).getUrl());
                        card.setSketchImageThree(note.getImage().get(2).getUrl());
                    }else if(note.getImage().size()==4){
                        card.setSketchImageOne(note.getImage().get(0).getUrl());
                        card.setSketchImageTwo(note.getImage().get(1).getUrl());
                        card.setSketchImageThree(note.getImage().get(2).getUrl());
                        card.setSketchImageFour(note.getImage().get(3).getUrl());
                    }
                    card.setTag(i);
                    parent.addView(card);
                    final int finalI = i;
                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //跳转到该笔记浏览页面
                            Intent intent = new Intent(SelfNoteScan.this,NoteScan.class);
                            intent.putExtra("userdata", (Serializable) list);
                            int k = (int) view.getTag();
                            intent.putExtra("id",k);
                            startActivity(intent);
                        }
                    });
                    card.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            deleteCurrentNote(view,note);
                            return true;
                        }
                    });
                }
            }
        });
    }
    //删除笔记
    private void deleteCurrentNote(final View view, final Note note) {
        if(user.getObjectId().equals(BmobUser.getCurrentUser(MyUser.class).getObjectId())){
            new AlertDialog.Builder(this)
                    .setTitle("删除笔记")
                    .setMessage("笔记被删除后，将无法恢复！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            parent.removeView(view);
                            DeleteDataBmob.deleteNote(note);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:
                finish();
                break;
            case R.id.my_setting_done:
                startActivity(new Intent(SelfNoteScan.this, ServiceHelp.class));
                break;
        }
    }
}
