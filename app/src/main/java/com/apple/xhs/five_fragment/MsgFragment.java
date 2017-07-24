package com.apple.xhs.five_fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apple.xhs.R;
import com.apple.xhs.msg_viewpager_fragment.MsgFgPagerAdapter;
import com.apple.xhs.msg_viewpager_fragment.MsgViewPgFgLeft;
import com.apple.xhs.msg_viewpager_fragment.MsgViewPgFgMid;
import com.apple.xhs.msg_viewpager_fragment.MsgViewPgFgRight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limeng on 2017/7/22.
 */

public class MsgFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    TextView tabLeft,tabMid,tabRight;
    ImageView line_tab;
    int moveOne = 0;
    boolean isScrolling = false;
    boolean isBackScrolling = false;
    long startTime = 0;
    long currentTime = 0;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg_layout,container,false);
        initView(view);
        initLineImg(view);
        return view;
    }

    private void initLineImg(View view) {
        line_tab = view.findViewById(R.id.msg_tab_line);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        ViewGroup.LayoutParams lp = line_tab.getLayoutParams();
        lp.width = screenW/3;
        line_tab.setLayoutParams(lp);
        moveOne = lp.width;
    }

    @SuppressLint("ResourceAsColor")
    private void initView(View view) {
        viewPager = view.findViewById(R.id.msg_viewpager);
        viewPager.setOnPageChangeListener(this);
        MsgViewPgFgLeft pgFgLeft = new MsgViewPgFgLeft();
        MsgViewPgFgMid pgFgMid = new MsgViewPgFgMid();
        MsgViewPgFgRight pgFgRight = new MsgViewPgFgRight();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(pgFgLeft);
        fragmentList.add(pgFgMid);
        fragmentList.add(pgFgRight);
        MsgFgPagerAdapter adapter = new MsgFgPagerAdapter(getFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        tabLeft = view.findViewById(R.id.tab_one);
        tabMid = view.findViewById(R.id.tab_two);
        tabRight = view.findViewById(R.id.tab_three);
        tabLeft.setTextColor(R.color.xhsColor);

        tabLeft.setOnClickListener(this);
        tabMid.setOnClickListener(this);
        tabRight.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        //setTabTextColor();
        switch (view.getId()){
            case R.id.tab_one:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tab_two:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tab_three:
                viewPager.setCurrentItem(2);
                break;
        }
    }
    @SuppressLint("ResourceAsColor")
    private void setTabTextColor(){
        tabLeft.setTextColor(R.color.blackColor);
        tabMid.setTextColor(R.color.blackColor);
        tabRight.setTextColor(R.color.blackColor);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentTime = System.currentTimeMillis();
        if(isScrolling&&currentTime-startTime>200){
            movePosition(position,moveOne*positionOffset);
            startTime = currentTime;
        }
        if(isBackScrolling){
            movePosition(position);
        }
    }

    private void movePosition(int position, float v) {
        float curTranslation = line_tab.getTranslationX();
        float toPosition = moveOne*position + v;
        ObjectAnimator animator = ObjectAnimator.ofFloat(line_tab,"translationX",curTranslation,toPosition);
        animator.setDuration(100);
        animator.start();
    }
    private void movePosition(int position) {
        movePosition(position,0);
    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                movePosition(0);
                break;
            case 1:
                movePosition(1);
                break;
            case 2:
                movePosition(2);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state){
            case 1:
                isScrolling = true;
                isBackScrolling = false;
                break;
            case 2:
                isScrolling = false;
                isBackScrolling = true;
                break;
            default:
                isScrolling = false;
                isBackScrolling = false;
        }
    }
}
