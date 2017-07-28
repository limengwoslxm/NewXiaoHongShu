package com.apple.xhs.msg_viewpager_fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/23.
 */

public class MsgViewPgFgRight extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msg_viewpager_right,null);
        ImageView img = view.findViewById(R.id.dodododo);
        AnimationDrawable drawable = (AnimationDrawable) img.getDrawable();
        drawable.start();
        return view;
    }
}
