package com.apple.xhs.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.apple.xhs.R;
import com.apple.xhs.adapter_util.AppBarStateChangeListener;
import com.base.BaseActivity;
import com.bean.MyUser;
import com.bean.Note;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.process.CircleImageProcessor;
import me.xiaopan.sketch.request.DisplayOptions;

/**
 * Created by limeng on 2017/7/30.
 */

public class NoteScan extends BaseActivity implements View.OnClickListener {
    //伸缩toolbar
    @BindView(R.id.note_appbar)
            AppBarLayout appBarLayout;
    @BindView(R.id.note_collapsing)
            CollapsingToolbarLayout collapsing;
    @BindView(R.id.note_toolbar)
            Toolbar toolbar;
    @BindView(R.id.playButton)
            ButtonBarLayout playButton;
    //user info
    @BindView(R.id.image)
            SketchImageView imageView;
    @BindView(R.id.userheadimage_toolbar)
            SketchImageView userheadimagetoolbar;
    @BindView(R.id.username_toolbar)
            TextView usernametoolbar;
    @BindView(R.id.userheadimage_context)
            SketchImageView userheadimagecontext;
    @BindView(R.id.username_context)
            TextView usernamecontext;
    @BindView(R.id.user_notetitle)
            TextView usernotetitle;
    @BindView(R.id.user_notecontext)
            TextView usernotecontext;

    View popView;
    PopupWindow popupWindow;
    MyUser myUser;
    @Override
    public int getContentViewId() {
        return R.layout.note_scan;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserHeadImage();
        setNoteData();
        setCollapsingToolbar();
        setAppbarCollapsing();
        setSharePopupwindow();
    }

    private void setUserHeadImage() {
        DisplayOptions displayOptions = new DisplayOptions();
        displayOptions.setImageProcessor(CircleImageProcessor.getInstance());
        imageView.getOptions().setDecodeGifImage(true);
        userheadimagetoolbar.setOptions(displayOptions);
        userheadimagecontext.setOptions(displayOptions);
    }

    private void setNoteData() {
        //获取intent传来的数据，设置页面
        List<Map<Note,MyUser>> userdata = (List<Map<Note,MyUser>>)getIntent().getSerializableExtra("userdata");
        int position = getIntent().getIntExtra("id",0);
        Note note = new Note();
        myUser = new MyUser();
        for (Map.Entry<Note,MyUser> entry : userdata.get(position).entrySet()){
            note = entry.getKey();
            myUser = entry.getValue();
        }
        imageView.displayImage(note.getImage().get(0).getUrl());
        userheadimagetoolbar.displayImage(myUser.getHead().getUrl());
        usernametoolbar.setText(myUser.getNickname());
        userheadimagecontext.displayImage(myUser.getHead().getUrl());
        usernamecontext.setText(myUser.getNickname());
        usernotetitle.setText(note.getTitle());
        usernotecontext.setText(note.getContent());
    }

    private void setAppbarCollapsing() {
        collapsing.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if(state == State.EXPANDED){
                    playButton.setVisibility(View.INVISIBLE);
                }else if(state == State.COLLAPSED){
                    playButton.setVisibility(View.VISIBLE);
                }else {

                }
            }
        });
    }
    //设置toolbar
    private void setCollapsingToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.note_share_menu){
                    popupWindow.showAtLocation(appBarLayout, Gravity.BOTTOM,0,0);
                    backgroundAlpha(0.6f);
                }
                return true;
            }
        });
    }
    //设置弹出popUpWindow
    private void setSharePopupwindow() {
        LayoutInflater inflater = getLayoutInflater();
        popView =inflater.inflate(R.layout.share_popupwindow,null);
        popupWindow = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.SharePopupWindow);
        popupWindow.setOnDismissListener(new MyPopUpWindow());
        popView.findViewById(R.id.quxiao).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.quxiao:
                popupWindow.dismiss();
                break;
        }
    }

    public class MyPopUpWindow implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
    //关注该用户
    public void guanZhu(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_toolbar_menu,menu);
        return true;
    }
}
