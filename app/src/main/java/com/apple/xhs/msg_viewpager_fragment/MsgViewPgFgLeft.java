package com.apple.xhs.msg_viewpager_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/23.
 */

public class MsgViewPgFgLeft extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msg_viewpager_left,null);
        return view;
    }
}
