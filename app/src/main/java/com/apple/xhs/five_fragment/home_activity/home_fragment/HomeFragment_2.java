package com.apple.xhs.five_fragment.home_activity.home_fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.apple.xhs.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by limeng on 2017/7/26.
 */

public class HomeFragment_2 extends Fragment {
    ImageView bubble1,bubble2,bubble3,bubble4;
    Button button;
    AnimatorSet set;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_viewp_itemv2,container,false);
        initView(view);
        startObjectAnimator();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    private void initView(View view) {
        bubble1 = view.findViewById(R.id.placeholder_bubble1);
        bubble2 = view.findViewById(R.id.placeholder_bubble2);
        bubble3 = view.findViewById(R.id.placeholder_bubble3);
        bubble4 = view.findViewById(R.id.placeholder_bubble4);
        button = view.findViewById(R.id.guanzhu_button);
        initViewListener();
    }
    public void startObjectAnimator() {
        ObjectAnimator alpha1 = ObjectAnimator.ofFloat(bubble1,"alpha",0.0f);
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(bubble2,"alpha",0.0f);
        ObjectAnimator alpha3 = ObjectAnimator.ofFloat(bubble3,"alpha",0.0f);
        ObjectAnimator alpha4 = ObjectAnimator.ofFloat(bubble4,"alpha",0.0f);
        AnimatorSet setInit = new AnimatorSet();
        setInit.playTogether(alpha1,alpha2,alpha3,alpha4);
        setInit.setDuration(10);
        setInit.start();
        ObjectAnimator alphaBubble1 = ObjectAnimator.ofFloat(bubble1,"alpha",0.0f,1.0f,0.0f);
        ObjectAnimator alphaBubble2 = ObjectAnimator.ofFloat(bubble2,"alpha",0.0f,1.0f,0.0f);
        ObjectAnimator alphaBubble3 = ObjectAnimator.ofFloat(bubble3,"alpha",0.0f,1.0f,0.0f);
        ObjectAnimator alphaBubble4 = ObjectAnimator.ofFloat(bubble4,"alpha",0.0f,1.0f,0.0f);
        set = new AnimatorSet();
        set.playSequentially(alphaBubble1,alphaBubble2,alphaBubble3,alphaBubble4);
        set.setDuration(8000);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                set.start();
            }
        });
        set.start();
    }

    private void initViewListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"开发中",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
