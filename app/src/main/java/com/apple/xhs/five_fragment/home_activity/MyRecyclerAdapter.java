package com.apple.xhs.five_fragment.home_activity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apple.xhs.R;
import com.bean.MyUser;
import com.bean.Note;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.process.CircleImageProcessor;
import me.xiaopan.sketch.request.DisplayOptions;

/**
 * Created by limeng on 2017/7/28.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
    List<Map<Note,MyUser>> data;
    View view;
    MyUser myUser;
    public MyRecyclerAdapter(List<Map<Note,MyUser>> data){
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grid_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i("data",data.size() + "data2");
        Note note = new Note();
        MyUser myUser = new MyUser();
        for (Map.Entry<Note,MyUser> entry : data.get(position).entrySet()){
            note = entry.getKey();
            myUser = entry.getValue();
        }
//        holder.imgPic.displayImage(note.getImage().get(0).getUrl());
        holder.textTitle.setText(note.getTitle());
        holder.textUserHead.displayImage(myUser.getHead().getUrl());
        holder.textUserName.setText(myUser.getNickname());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SketchImageView imgPic;
        TextView textTitle;
        TextView textUserName;
        SketchImageView textUserHead;
        public MyViewHolder(View itemView) {
            super(itemView);
            //
            imgPic = itemView.findViewById(R.id.grid_item_pic);
            ViewGroup.LayoutParams params = imgPic.getLayoutParams();
            params.height = (int)(400+ Math.random()*300);
            imgPic.setLayoutParams(params);
            //
            textTitle = itemView.findViewById(R.id.grid_item_textTitle);
            textUserName = itemView.findViewById(R.id.grid_item_user);
            //
            textUserHead = itemView.findViewById(R.id.grid_item_head);
            DisplayOptions displayOptions = new DisplayOptions();
            displayOptions.setImageProcessor(CircleImageProcessor.getInstance());
            textUserHead.setOptions(displayOptions);
        }
    }
}
