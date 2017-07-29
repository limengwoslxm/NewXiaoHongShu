package com.apple.xhs.five_fragment.view_util;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by limeng on 2017/7/28.
 */

public class MyLoopViewAdapter extends PagerAdapter {
    private List<ImageView> list;
    public MyLoopViewAdapter(List<ImageView> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= list.size();
        if(position < 0){
            position = list.size() + position;
        }
        ImageView view = list.get(position);
        ViewParent vp = view.getParent();
        if(vp != null){
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }
}
