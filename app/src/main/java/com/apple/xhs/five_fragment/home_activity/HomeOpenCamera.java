package com.apple.xhs.five_fragment.home_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.apple.xhs.MainActivity;
import com.apple.xhs.R;
import com.apple.xhs.note.NoteEditView;
import com.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by limeng on 2017/7/27.
 */

public class HomeOpenCamera extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.home_close_camera)
    ImageView close;
    @BindView(R.id.home_note)
    ImageView note;
    @BindView(R.id.home_video)
    ImageView video;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewListener();
    }

    private void setViewListener() {
        close.setOnClickListener(this);
        note.setOnClickListener(this);
        video.setOnClickListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.home_camera;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_close_camera:
                finish();
                this.overridePendingTransition(R.anim.home_camera_close,R.anim.home_camera_back);
                break;
            case R.id.home_note:
            case R.id.home_video:
                startActivity(new Intent(this, NoteEditView.class));
                finish();
                break;
        }
    }
}
