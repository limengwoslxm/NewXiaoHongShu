package com.apple.xhs.five_fragment.home_activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apple.xhs.R;
import com.bean.Note;

import java.util.List;

import me.xiaopan.sketch.SketchImageView;

/**
 * Created by limeng on 2017/7/28.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
    List<Note> data;

    public MyRecyclerAdapter(List<Note> data){
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grid_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.imgPic.displayImage(data.get(position).getImage().get(0).getUrl());
        holder.textTitle.setText(data.get(position).getTitle());
        holder.textContext.setText(data.get(position).getContent());
        holder.textUserName.setText(data.get(position).getAuthor().getNickname());
        //holder.textUserHead.displayImage(data.get(position).getAuthor().getHead().getUrl());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SketchImageView imgPic;
        TextView textTitle;
        TextView textContext;
        TextView textUserName;
        SketchImageView textUserHead;
        public MyViewHolder(View itemView) {
            super(itemView);
            imgPic = itemView.findViewById(R.id.grid_item_pic);
            ViewGroup.LayoutParams params = imgPic.getLayoutParams();
            //int height = params.height;
            params.height = (int)(300+ Math.random()*300);
            imgPic.setLayoutParams(params);
            textTitle = itemView.findViewById(R.id.grid_item_textTitle);
            textContext = itemView.findViewById(R.id.grid_item_textContext);
            textUserName = itemView.findViewById(R.id.grid_item_user);
            textUserHead = itemView.findViewById(R.id.grid_item_head);
        }
    }
}
