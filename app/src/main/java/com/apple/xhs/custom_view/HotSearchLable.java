package com.apple.xhs.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/8/4.
 */

public class HotSearchLable extends LinearLayout {
    TextView textView;
    public HotSearchLable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.HotSearchLable);
        String text = array.getString(R.styleable.HotSearchLable_setHotLable);
        LayoutInflater.from(context).inflate(R.layout.hotsearchlabelmerge,this);
        textView = findViewById(R.id.searchlable);
        textView.setText(text);
    }

    public void setTextView(String s) {
        textView.setText(s);
    }

    public String getTextView() {
        return textView.getText().toString();
    }
}
