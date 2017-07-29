package com.apple.xhs.five_fragment.home_fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apple.xhs.R;
import com.apple.xhs.five_fragment.home_activity.MyRecyclerAdapter;
import com.apple.xhs.five_fragment.home_activity.NoteInfo;
import com.bean.Note;
import com.bean.Style;
import com.data.SelectDataBmob;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.data.SelectDataBmob.getStyleId;

/**
 * Created by limeng on 2017/7/26.
 */

public class HomeFragment_1 extends Fragment {
    View view;
    RecyclerView recyclerView;
    MyRecyclerAdapter adapter;
    List<Note> data = new ArrayList<>();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                data= (List<Note>) msg.obj;
                initPagerView();
                //adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getNoteByStyle("武汉");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.home_viewp_itemv1,container,false);
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

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.homeFragment1);
    }
    private void initPagerView() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new MyRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);
        SpacesItemDecoration space = new SpacesItemDecoration(20);
        recyclerView.addItemDecoration(space);
    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
        int space;
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
            public void done(List<Note> list, BmobException e) {
                if(e==null){
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    message.obj = list;
                    handler.sendMessage(message);
                }else{
                    Log.i("bmob",e + "");
                }
            }
        });
    }
}
