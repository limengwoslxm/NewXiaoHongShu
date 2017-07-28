package com.apple.xhs.msg_viewpager_fragment;

import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/23.
 */

public class MsgViewPgFgLeft extends Fragment {
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msg_viewpager_left,null);
        textView = view.findViewById(R.id.theEnd);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/AgroableRough.ttf");
        textView.setTypeface(typeface);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        ObjectAnimator.ofFloat(textView,"translationX",0,-20,40,-35,50,-25,20,-15,10,0)
                                .setDuration(1000)
                                .start();
                        break;
                }
                return false;
            }
        });
        return view;
    }

}
