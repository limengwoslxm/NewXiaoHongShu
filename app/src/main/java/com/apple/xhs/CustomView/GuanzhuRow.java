package com.apple.xhs.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/26.
 */

public class GuanzhuRow extends LinearLayout {
    public GuanzhuRow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.GuanzhuRow);
        Drawable drawable = array.getDrawable(R.styleable.GuanzhuRow_setHeart);
        String name = array.getString(R.styleable.GuanzhuRow_setName);
        String fensi = array.getString(R.styleable.GuanzhuRow_setFensi);

        LayoutInflater.from(context).inflate(R.layout.guanzhu_mysettingview,this);
        ImageView img = findViewById(R.id.guanzhu_user_heard);
        TextView textName = findViewById(R.id.guanzhu_user_name);
        TextView textFensi = findViewById(R.id.guanzhu_user_fensi);

        img.setImageDrawable(drawable);
        textName.setText(name);
        textFensi.setText(fensi);
    }

}
