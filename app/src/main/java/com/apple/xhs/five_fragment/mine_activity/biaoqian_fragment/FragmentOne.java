package com.apple.xhs.five_fragment.mine_activity.biaoqian_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/26.
 */

public class FragmentOne extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guanzhu_user,container,false);
        return view;
    }
}
