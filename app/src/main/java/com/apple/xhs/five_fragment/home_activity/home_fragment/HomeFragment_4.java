package com.apple.xhs.five_fragment.home_activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.xhs.R;
import com.apple.util.MyRecyclerViewAdapter;
import com.apple.xhs.note.NoteScan;
import com.bean.MyUser;
import com.bean.Note;
import com.bean.Style;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.data.SelectDataBmob.getStyleId;

/**
 * Created by limeng on 2017/7/26.
 */

public class HomeFragment_4 extends Fragment implements MyRecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    View view;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    SwipeRefreshLayout swiperefreshlayout;
    List<Map<Note,MyUser>> data = new ArrayList<>();
    SpacesItemDecoration space;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                data = (List<Map<Note, MyUser>>) msg.obj;
                Log.i("data1",data.size() + "data");
                initPagerView();
                //adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getNoteByStyle("男人");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.home_viewp_itemv4,container,false);
            initView(view);
        }else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if(viewGroup != null){
                viewGroup.removeView(view);
            }
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.homeFragment4);
        swiperefreshlayout = view.findViewById(R.id.swiperefreshlayout4);
        swiperefreshlayout.setColorSchemeColors(getResources().getColor(R.color.xhsColor));
        swiperefreshlayout.setOnRefreshListener(this);
    }
    private void initPagerView() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        if(space==null){
            space = new SpacesItemDecoration(20);
            recyclerView.addItemDecoration(space);
        }
        adapter = new MyRecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), NoteScan.class);
        intent.putExtra("userdata", (Serializable) data);
        intent.putExtra("id",position);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getNoteByStyle("男人");
                initPagerView();
                swiperefreshlayout.setRefreshing(false);
            }
        },2000);
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
    public void getNoteByStyle(String styleName){
        BmobQuery<Note> query = new BmobQuery<Note>();
        Style style = new Style();
        style.setObjectId(getStyleId(styleName));
        query.addWhereRelatedTo("note",new BmobPointer(style));
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(final List<Note> notelist, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询笔记成功" + notelist.size());
                    List<BmobQuery<MyUser>> userQuery = new ArrayList<>();
                    for (Note note:notelist){
                        userQuery.add(new BmobQuery<MyUser>().addWhereEqualTo("objectId",note.getAuthor().getObjectId()));
                    }
                    final BmobQuery<MyUser> mainQuery = new BmobQuery<MyUser>();
                    mainQuery.or(userQuery);
                    mainQuery.findObjects(new FindListener<MyUser>() {
                        @Override
                        public void done(List<MyUser> userlist, BmobException e) {
                            if(e==null){
                                Log.i("bmob","查询作者成功" + userlist.size());
                                List<Map<Note,MyUser>> maps = new ArrayList<>();
                                for (int i = 0;i < notelist.size();i++){
                                    for (int j = 0;j < userlist.size();j++){
                                        if (notelist.get(i).getAuthor().getObjectId().equals(userlist.get(j).getObjectId())){
                                            Log.i("bmob","note = " + notelist.get(i).getImage().get(0).getFilename());
                                            Map<Note,MyUser> map = new HashMap<>();
                                            map.put(notelist.get(i),userlist.get(j));
                                            maps.add(map);
                                        }
                                    }
                                }
                                Log.i("data1",maps.size() + "");
                                Message message = handler.obtainMessage();
                                message.what = 1;
                                message.obj = maps;
                                handler.sendMessage(message);
                            }else{
                                Log.i("bmob","查询作者失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }else{
                    Log.i("bmob",e + "查询笔记失败");
                }
            }
        });
    }
}

