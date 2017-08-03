package com.apple.xhs.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/24.
 */

public class InfoSettingTitle extends RelativeLayout {
    View parent,img;
    TextView textView,textViewDone;

    public InfoSettingTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUiView(context,attrs);
    }

    @SuppressLint("NewApi")
    private void initUiView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InfoSettingTitle);
        String text = array.getString(R.styleable.InfoSettingTitle_setText);
        String textdone = array.getString(R.styleable.InfoSettingTitle_setDoneText);
        Drawable drawable = array.getDrawable(R.styleable.InfoSettingTitle_setDoneTextBackground);
        Drawable drawable1 = array.getDrawable(R.styleable.InfoSettingTitle_setBackground);
        LayoutInflater.from(context).inflate(R.layout.mine_infosettingtitle,this);
        parent = findViewById(R.id.mySettingViewTitle);
        img = findViewById(R.id.my_setting_back);
        textView = (TextView) findViewById(R.id.my_setting_title);
        textViewDone = (TextView) findViewById(R.id.my_setting_done);
        textView.setText(text);
        textViewDone.setText(textdone);
        textViewDone.setBackground(drawable);
        parent.setBackground(drawable1);
    }

    public void setTitleText(String str){
        textView.setText(str);
    }
    public void setImgListener(OnClickListener listener) {
        img.setOnClickListener(listener);
    }
    public void setDoneListener(OnClickListener listener) {
        textViewDone.setOnClickListener(listener);
    }

    public TextView getTextView() {
        return textView;
    }
}
