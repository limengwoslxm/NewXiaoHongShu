package com.apple.xhs.five_fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apple.xhs.R;
import com.apple.xhs.five_fragment.view_util.MyLoopViewAdapter;
import com.apple.xhs.five_fragment.view_util.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limeng on 2017/7/22.
 */

public class StoreFragment extends Fragment  {
    //轮播图
    private int[] views = new int[]{R.mipmap.store_viewpager_1,R.mipmap.store_viewpager_2,R.mipmap.store_viewpager_3,
            R.mipmap.store_viewpager_4,R.mipmap.store_viewpager_5,R.mipmap.store_viewpager_6,
            R.mipmap.store_viewpager_7,R.mipmap.store_viewpager_8};
    ViewPager viewPager;
    private List<ImageView> viewList = new ArrayList<>();
    private int currentViewId = 0;
    private Handler handler = new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_layout,container,false);
        initData();
        initView(view);
        vipAutoToNext();
        return view;
    }

    private void initData() {
        for(int v:views){
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(v);
            viewList.add(imageView);
        }
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.store_viewpager);
        PagerAdapter adapter = new MyLoopViewAdapter(viewList);
        viewPager.setAdapter(adapter);
    }

    private void vipAutoToNext() {
        handler.postDelayed(new MyRunnable(),4000);
    }
    public class MyRunnable implements Runnable{
        @Override
        public void run() {
            if(currentViewId < 100 && currentViewId == viewPager.getCurrentItem()){
                viewPager.setCurrentItem(++currentViewId);
            }else if(currentViewId < 100 && currentViewId != viewPager.getCurrentItem()){
                currentViewId = viewPager.getCurrentItem();
                viewPager.setCurrentItem(++currentViewId);
            }
            handler.postDelayed(this,4000);
        }
    }
}
