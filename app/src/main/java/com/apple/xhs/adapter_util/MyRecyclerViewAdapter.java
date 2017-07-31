package com.apple.xhs.adapter_util;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apple.xhs.R;
import com.bean.MyUser;
import com.bean.Note;

import java.util.List;
import java.util.Map;

import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.process.CircleImageProcessor;
import me.xiaopan.sketch.request.DisplayOptions;

/**
 * Created by limeng on 2017/7/28.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener{
    List<Map<Note,MyUser>> data;
    View view;
    public MyRecyclerViewAdapter(List<Map<Note,MyUser>> data){
        this.data = data;
    }
    private OnItemClickListener mOnItemClickListener = null;

    //定义一个interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grid_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
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
        Log.i("bmob",note.getImage().get(0) + "new");
        holder.imgPic.displayImage(note.getImage().get(0).getUrl());
        holder.textTitle.setText(note.getTitle());
        holder.textUserHead.displayImage(myUser.getHead().getUrl());
        holder.textUserName.setText(myUser.getNickname());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        SketchImageView imgPic;
        TextView textTitle;
        TextView textUserName;
        SketchImageView textUserHead;
        public MyViewHolder(View itemView) {
            super(itemView);
            // 获取img设置
            imgPic = itemView.findViewById(R.id.grid_item_pic);
//            ViewGroup.LayoutParams params = imgPic.getLayoutParams();
//            params.height = (int)(200+ Math.random()*600);
//            imgPic.setLayoutParams(params);
            imgPic.setScaleType(SketchImageView.ScaleType.CENTER_CROP);
            imgPic.setAdjustViewBounds(true);
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
