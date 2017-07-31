package com.apple.xhs.five_fragment.search_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class SearchFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView addFriend;
    private ViewPager viewPager;
    private int[] images = new int[]{R.mipmap.search_v7,R.mipmap.search_v1,R.mipmap.search_v2,R.mipmap.search_v3,R.mipmap.search_v4,
            R.mipmap.search_v5,R.mipmap.search_v6,R.mipmap.search_v7,R.mipmap.search_v1};
    private List<ImageView> list = new ArrayList<>();
    Handler handler = new Handler();
    int currentItem ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_layout,container,false);
        initData();
        initView(view);
        viewPagerAdapterSet();
        return view;
    }

    private void initView(View view) {
        addFriend = view.findViewById(R.id.search_add_friend);
        addFriend.setOnClickListener(this);
        viewPager = view.findViewById(R.id.search_viewpager);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_add_friend:
                startActivity(new Intent(getActivity(), SearchAdd.class));
                break;
        }
    }

    private void initData() {
        for (int id : images){
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(id);
            list.add(imageView);
        }
    }

    private void viewPagerAdapterSet() {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        handler.postDelayed(new MyRunnable(),5000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == 0 && positionOffsetPixels == 0) {
            viewPager.setCurrentItem(list.size() - 2, false);
            currentItem = list.size()-2;
        }
        else if (position == list.size() - 1 && positionOffsetPixels == 0){
            viewPager.setCurrentItem(1, false);
            currentItem = 1;
        }
    }

    @Override
    public void onPageSelected(final int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyRunnable implements Runnable{
        @Override
        public void run() {
            currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(++currentItem);
            handler.postDelayed(this,3500);
        }
    }

}
