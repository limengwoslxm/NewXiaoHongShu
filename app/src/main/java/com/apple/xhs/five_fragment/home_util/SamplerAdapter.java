package com.apple.xhs.five_fragment.home_util;

import android.content.Context;
import android.util.Printer;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apple.xhs.R;
import com.bean.MyUser;
import com.bean.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limeng on 2017/7/24.
 */

public class SamplerAdapter extends ArrayAdapter implements View.OnClickListener {

    static class ViewHolder{
        ImageView pic;
        TextView title;
        TextView picName;
        TextView heartLike;
    }
    private final LayoutInflater layoutInflater;
    private static final SparseArray<Double> sPositionHeight = new SparseArray<>();
    private final List<MyUser> myUsers = new ArrayList<>();
    private final List<Note> notes = new ArrayList<>();

    public SamplerAdapter(Context context, int resource,List<MyUser> myUsers,List<Note> notes){
        super(context,resource);
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view = layoutInflater.inflate(R.layout.home_grid_item,viewGroup,false);
            holder = new ViewHolder();
            holder.pic = view.findViewById(R.id.grid_item_pic);
            holder.title = view.findViewById(R.id.grid_item_text);
            holder.picName = view.findViewById(R.id.grid_item_user);
            holder.heartLike = view.findViewById(R.id.grid_item_heart);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.picName.setOnClickListener(this);
        holder.heartLike.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.grid_item_user:
                break;
            case R.id.grid_item_heart:
                break;
        }
    }
}
