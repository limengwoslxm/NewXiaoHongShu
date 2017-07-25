package com.apple.xhs.five_fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.apple.xhs.R;
import com.apple.xhs.five_fragment.home_util.HomePagerViewAdapter;
import com.apple.xhs.five_fragment.home_util.SamplerAdapter;
import com.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by limeng on 2017/7/22.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    View popUpView,homeTop,popUpDismiss1,popUpDismiss2;
    ViewPager viewPager;
    PopupWindow popupWindow;
    WindowManager windowManager;
    StaggeredGridView gridView;
    SamplerAdapter adapter;
    View hideLine;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout,container,false);
        initViewPopWindow(view);
        initViewPager(view);
        return view;
    }

    private void initViewPager(View view) {
        LayoutInflater inflater = getLayoutInflater();
        gridView = (StaggeredGridView) inflater.inflate(R.layout.home_grid_view,null);


        viewPager = (ViewPager) view.findViewById(R.id.home_viewpager);
        List<View> data = new ArrayList<>();
        data.add(gridView);
        HomePagerViewAdapter adapter = new HomePagerViewAdapter(data);
        viewPager.setAdapter(adapter);
    }
    //弹出窗口
    private void initViewPopWindow(View view) {
        view.findViewById(R.id.home_scrollview_showMore).setOnClickListener(this);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popUpView = layoutInflater.inflate(R.layout.home_scrollview_show_more,null);
        windowManager = getActivity().getWindowManager();
        popupWindow = new PopupWindow(popUpView,windowManager.getDefaultDisplay().getWidth(),
                windowManager.getDefaultDisplay().getHeight());
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        //弹出框显示在该控件下方
        homeTop = view.findViewById(R.id.home_top);
        initViewOthers(popUpView);
    }
    private void initViewOthers(View view) {
        popUpDismiss1 = view.findViewById(R.id.popUpDismiss1);
        popUpDismiss2 = view.findViewById(R.id.popUpDismiss2);
        hideLine = view.findViewById(R.id.home_line_hide);
        popUpDismiss1.setOnClickListener(this);
        popUpDismiss2.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_scrollview_showMore:
                hideLine.setVisibility(View.INVISIBLE);
                popupWindow.showAsDropDown(homeTop);
                break;
            case R.id.popUpDismiss1:
            case R.id.popUpDismiss2:
                hideLine.setVisibility(View.VISIBLE);
                popupWindow.dismiss();
                break;
        }
    }
}
