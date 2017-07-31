package com.apple.xhs.five_fragment.store_activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apple.xhs.R;
import com.apple.xhs.adapter_util.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limeng on 2017/7/22.
 */

public class StoreFragment extends Fragment implements ViewPager.OnPageChangeListener {
    //轮播图
    private int[] views = new int[]{R.mipmap.store_viewpager_8,R.mipmap.store_viewpager_1, R.mipmap.store_viewpager_2, R.mipmap.store_viewpager_3,
            R.mipmap.store_viewpager_4, R.mipmap.store_viewpager_5, R.mipmap.store_viewpager_6,
            R.mipmap.store_viewpager_7, R.mipmap.store_viewpager_8,R.mipmap.store_viewpager_1};
    ViewPager viewPager;
    private List<ImageView> list = new ArrayList<>();
    Handler handler = new Handler();
    int currentItem ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_layout, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        for (int v : views) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(v);
            list.add(imageView);
        }
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.store_viewpager);
        viewPager.addOnPageChangeListener(this);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        handler.postDelayed(new MyRunnable(),5000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //position=0与position=倒数第2相同，1与倒数第一相同
        if (position == 0 && positionOffsetPixels == 0) {
            viewPager.setCurrentItem(list.size() - 2, false);
            currentItem = list.size()-2;
        }
        else if (position == list.size() - 1 && positionOffsetPixels == 0) {
            viewPager.setCurrentItem(1, false);
            currentItem = 1;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public class MyRunnable implements Runnable{
        @Override
        public void run() {
            //1、初始化获取的为0，执行++后为1，即显示（实际的）第一张图；2、若图片被人为滑动，则定位到当前pager，顺序往后走。
            currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(++currentItem);
            handler.postDelayed(this,3500);
        }
    }
}