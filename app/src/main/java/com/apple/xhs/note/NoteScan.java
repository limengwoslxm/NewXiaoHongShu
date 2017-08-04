package com.apple.xhs.note;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.apple.initbmob.InitBmob;
import com.apple.util.AppBarStateChangeListener;
import com.apple.util.MySketchViewPagerAdapter;
import com.apple.xhs.R;
import com.apple.xhs.custom_view.CommentModule;
import com.base.BaseActivity;
import com.bean.Comment;
import com.bean.MyUser;
import com.bean.Note;
import com.data.AddDataBmob;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.process.CircleImageProcessor;
import me.xiaopan.sketch.request.DisplayOptions;

/**
 * Created by limeng on 2017/7/30.
 */

public class NoteScan extends BaseActivity implements View.OnClickListener, View.OnLayoutChangeListener {
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
    @BindView(R.id.pic_viewpager)
    ViewPager pic_viewpager;
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
    @BindView(R.id.note_click_pinglun)
    TextView noteclickpingl;
    @BindView(R.id.note_popedittext)
    LinearLayout popedittext;
    @BindView(R.id.edittext)
            EditText editText;
    @BindView(R.id.note_pinglunparent)
            LinearLayout note_pinglunparent;
    @BindView(R.id.note_fabupinglun)
            Button note_fabupinglun;
    @BindView(R.id.current_userhead_img)
            SketchImageView current_userhead_img;
    @BindView(R.id.pinglunSum)
            TextView pinglunSum;
    @BindView(R.id.show_morePinglun)
            TextView show_morePinglun;
    @BindView(R.id.note_guanzhu)
            Button guanzhu;
    Note note;
    //当前笔记作者
    MyUser myUser;
    //当前app用户
    MyUser currentUser;
    View popView;
    PopupWindow popupWindow;
    List<SketchImageView> list = new ArrayList<>();
    int keyHeight;
    DisplayOptions displayOptions;
    List<Comment> allpinglun = new ArrayList<>();
    boolean isGuanzhu = false;
    @Override
    public int getContentViewId() {
        return R.layout.note_scan;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = BmobUser.getCurrentUser(MyUser.class);
        setUserHeadImage();
        setNoteData();
        setCollapsingToolbar();
        setAppbarCollapsing();
        setSharePopupwindow();
        setListener();
    }

    private void setListener() {
        popedittext.addOnLayoutChangeListener(this);
        noteclickpingl.setOnClickListener(this);
        note_fabupinglun.setOnClickListener(this);
        pinglunSum.setOnClickListener(this);
        show_morePinglun.setOnClickListener(this);
        userheadimagetoolbar.setOnClickListener(this);
        usernametoolbar.setOnClickListener(this);
        userheadimagecontext.setOnClickListener(this);
        usernamecontext.setOnClickListener(this);
        guanzhu.setOnClickListener(this);
    }

    private void setUserHeadImage() {
        displayOptions = new DisplayOptions();
        displayOptions.setImageProcessor(CircleImageProcessor.getInstance());
        //sketchimageview.getOptions().setDecodeGifImage(true);
        userheadimagetoolbar.setOptions(displayOptions);
        userheadimagecontext.setOptions(displayOptions);
        current_userhead_img.setOptions(displayOptions);
        keyHeight = getWindowManager().getDefaultDisplay().getHeight()/3;
    }

    private void setNoteData() {
        //获取intent传来的数据，设置页面
        List<Note> userdata = (List<Note>)getIntent().getSerializableExtra("userdata");
        int position = getIntent().getIntExtra("id",0);
        note = userdata.get(position);
        myUser = userdata.get(position).getAuthor();
        for(int i = 0; i < note.getImage().size();i++){
            SketchImageView imageView = new SketchImageView(this);
            imageView.displayImage(note.getImage().get(i).getUrl());
            imageView.getOptions().setDecodeGifImage(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            final int j = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NoteScan.this,NoteEditShowBigPic.class);
                    intent.putExtra("showbigpic",note.getImage().get(j).getUrl());
                    startActivity(intent);
                    overridePendingTransition(R.anim.showbigpic_in,R.anim.showbigpic_out);
                }
            });
            list.add(imageView);
        }
        MySketchViewPagerAdapter adapter = new MySketchViewPagerAdapter(list);
        pic_viewpager.setAdapter(adapter);
        //sketchimageview.displayImage(note.getImage().get(0).getUrl());
        //评论区头像设为当前用户
        current_userhead_img.displayImage(currentUser.getHead().getUrl());
        userheadimagetoolbar.displayImage(myUser.getHead().getUrl());
        usernametoolbar.setText(myUser.getNickname());
        userheadimagecontext.displayImage(myUser.getHead().getUrl());
        usernamecontext.setText(myUser.getNickname());
        usernotetitle.setText(note.getTitle());
        usernotecontext.setText(note.getContent());
        addThisNotePinglunList(note);
    }

    private void addThisNotePinglunList(Note note) {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.addWhereEqualTo("note",note);
        query.order("-createdAt");
        query.include("user");
        //query.setLimit(3);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(list==null){
                    return;
                }
                allpinglun = list;
                pinglunSum.setText("共"+list.size()+"条评论");
                show_morePinglun.setText("查看全部"+list.size()+"条评论");
                if (e==null){
                    for(int i = 0;i < list.size() && i < 3 ;i++){
                        Comment comment = list.get(i);
                        final String url = comment.getUser().getHead().getUrl();
                        final String nickname = comment.getUser().getNickname();
                        final String createdAt = comment.getCreatedAt();
                        final String content = comment.getContent();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CommentModule module = new CommentModule(getApplicationContext(),null);
                                module.getHeadPic().setOptions(displayOptions);
                                module.getHeadPic().displayImage(url);
                                module.getUserContent().setText(content);
                                module.getUserName().setText(nickname);
                                module.getPushDate().setText(createdAt);
                                note_pinglunparent.addView(module);
                            }
                        });
                    }
                    //Toast.makeText(InitBmob.getContext(),"查询到" + list.size() + "条评论",Toast.LENGTH_SHORT).show();
                }else {
                    Log.i("bmob","查询评论失败" + e.getErrorCode() + e.getMessage());
                }
            }
        });
    }

    private void setAppbarCollapsing() {
        collapsing.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if(state == State.EXPANDED){
                    playButton.setVisibility(View.GONE);
                }else if(state == State.COLLAPSED){
                    playButton.setVisibility(View.VISIBLE);
                }else {
                    playButton.setVisibility(View.GONE);
                }
            }
        });
    }
    //设置toolbar
    private void setCollapsingToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //右菜单，从底部弹出分享
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
                //分享弹出框处理
            case R.id.quxiao:
                popupWindow.dismiss();
                break;
            case R.id.note_click_pinglun:
                //弹出软键盘
                popedittext.setVisibility(View.VISIBLE);
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
                //点击发布评论按钮
            case R.id.note_fabupinglun:
                String pingluncontent = editText.getText().toString();
                if(pingluncontent.equals("")){
                    Toast.makeText(this,"空空如也",Toast.LENGTH_SHORT).show();
                    return;
                }
                addPingLunContentToView(pingluncontent);
                break;
            case R.id.pinglunSum:
            case R.id.show_morePinglun:
                if(allpinglun.size()==0){
                    Toast.makeText(this,"还没有评论哦",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(NoteScan.this,NoteAllPinglun.class);
                intent.putExtra("allpinglun", (Serializable) allpinglun);
                startActivity(intent);
                break;
            case R.id.userheadimage_context:
            case R.id.userheadimage_toolbar:
            case R.id.username_context:
            case R.id.username_toolbar:
                Intent notelist = new Intent(NoteScan.this,SelfNoteScan.class);
                notelist.putExtra("userselfnote",myUser);
                startActivity(notelist);
                break;
            case R.id.note_guanzhu:
                guanZhu();
                break;
        }
    }
    // 添加用户提交的评论到界面
    private void addPingLunContentToView(String s) {
        CommentModule layout = new CommentModule(this,null);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateTime = format.format(new Date());
        layout.getHeadPic().setOptions(displayOptions);
        layout.getHeadPic().displayImage(currentUser.getHead().getUrl());
        layout.getUserName().setText(currentUser.getNickname());
        layout.getUserContent().setText(s);
        layout.getPushDate().setText(dateTime);
        note_pinglunparent.addView(layout,0);
        AddDataBmob.addComment(note,s);
        editText.setText("");
        popedittext.setVisibility(View.INVISIBLE);
        InputMethodManager imm2 = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm2.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        Toast.makeText(this,"评论成功",Toast.LENGTH_SHORT).show();
    }

    //监听软键盘是否谈起
    @Override
    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        if(i7 != 0 && i3 != 0 &&(i7 - i3 > keyHeight)){
            //软键盘打开
            popedittext.setVisibility(View.VISIBLE);
        }else if(i7 != 0 && i3 != 0 &&(i3 - i7 > keyHeight)){
            //软键盘关闭
            popedittext.setVisibility(View.GONE);
        }
    }
    // 弹出框消失后恢复activitiy颜色
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
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void guanZhu(){
        if(isGuanzhu==false){
            guanzhu.setText("已关注");
            guanzhu.setTextColor(getResources().getColor(R.color.gray));
            guanzhu.setBackground(getResources().getDrawable(R.drawable.cancelguanzhu_buttonshape));
            isGuanzhu = true;
        }else {
            guanzhu.setText("+ 关注");
            guanzhu.setTextColor(getResources().getColor(R.color.xhsColor));
            guanzhu.setBackground(getResources().getDrawable(R.drawable.addguanzhu_buttonshape));
            isGuanzhu = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_toolbar_menu,menu);
        return true;
    }
}