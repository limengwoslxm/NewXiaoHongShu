package com.apple.util;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.xiaopan.sketch.SketchImageView;

/**
 * Created by limeng on 2017/8/2.
 */

public class MySketchViewPagerAdapter extends PagerAdapter {
    List<SketchImageView> list;
    public MySketchViewPagerAdapter(List<SketchImageView> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}
