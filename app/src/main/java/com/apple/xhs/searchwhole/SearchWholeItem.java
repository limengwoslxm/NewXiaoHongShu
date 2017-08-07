package com.apple.xhs.searchwhole;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apple.util.MyRecyclerViewAdapter;
import com.apple.xhs.R;
import com.apple.xhs.custom_view.InfoSettingTitle;
import com.apple.xhs.five_fragment.home_activity.home_fragment.HomeFragment_1;
import com.apple.xhs.note.NoteScan;
import com.base.BaseActivity;
import com.bean.Note;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.data.UpdateDataBmob.handler;

/**
 * Created by limeng on 2017/8/4.
 */

public class SearchWholeItem extends BaseActivity implements View.OnClickListener, MyRecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.searchitemtoolbar)
    InfoSettingTitle toolbar;
    @BindView(R.id.searchresultview)
    RecyclerView searchresultview;
    @BindView(R.id.norelated)
    TextView norelated;

    String toolbarText;
    List<Note> getIntentList = new ArrayList<>();
    List<Note> queryList = new ArrayList<>();
    SpacesItemDecoration space;
    MyRecyclerViewAdapter adapter;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                queryList = (List<Note>) msg.obj;
                initView(queryList);
            }
        }
    };
    @Override
    public int getContentViewId() {
        return R.layout.searchwholeitem;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setListener();
    }

    private void initData() {
        Intent intent = getIntent();
        toolbarText = intent.getStringExtra("lable");
        if(toolbarText.equals("allrelated")){
            getIntentList = (List<Note>) intent.getSerializableExtra("notelist");
            toolbar.setTitleText("全部");
            initView(getIntentList);
        }else {
            toolbar.setTitleText(toolbarText);
            queryDate(toolbarText);
        }
    }

    private void queryDate(final String s) {
        BmobQuery<Note> query1 = new BmobQuery<Note>();
        query1.include("author");
        query1.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e==null){
                    queryList = new ArrayList<>();
                    for (Note note:list){
                        if (note.getTitle().contains(s)){
                            queryList.add(note);
                        }
                    }
                    if (queryList.size()==0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                norelated.setVisibility(View.VISIBLE);
                            }
                        });
                    }else {
                        //代码块
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.obj = queryList;
                        handler.sendMessage(message);
                    }
                }else {
                    Log.i("bmob","模糊查询失败" + e.getErrorCode() + e.getMessage());
                }
            }
        });
    }

    private void initView(List<Note> list) {
        searchresultview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        if(space==null){
            space = new SpacesItemDecoration(20);
            searchresultview.addItemDecoration(space);
        }
        adapter = new MyRecyclerViewAdapter(list);
        searchresultview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void setListener() {
        toolbar.setImgListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:
                finish();
                break;

        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, NoteScan.class);
        intent.putExtra("userdata", queryList.get(position));
        startActivity(intent);
    }

    //设置item外边距
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
        int space = 0;
        public SpacesItemDecoration(int space){
            this.space = space;
        }
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space*2;
            if(parent.getChildAdapterPosition(view)==0||parent.getChildAdapterPosition(view)==1){
                outRect.top=space;
            }
        }
    }
}
