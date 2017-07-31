package com.apple.xhs.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.apple.xhs.R;
import com.apple.xhs.adapter_util.AppBarStateChangeListener;
import com.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by limeng on 2017/7/30.
 */

public class NoteScan extends BaseActivity {
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.note_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.note_collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.note_toolbar)
    Toolbar toolbar;
    @BindView(R.id.playButton)
    ButtonBarLayout playButton;

    @Override
    public int getContentViewId() {
        return R.layout.note_scan;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCollapsingToolbar();
        setAppbarCollapsing();
    }

    private void setCollapsingToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.note_share_menu){
                    //弹出分享页面
                }
                return true;
            }
        });
    }

    private void setAppbarCollapsing() {
        collapsing.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if(state == State.EXPANDED){
                    playButton.setVisibility(View.INVISIBLE);
                }else if(state == State.COLLAPSED){
                    playButton.setVisibility(View.VISIBLE);
                }else {

                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_toolbar_menu,menu);
        return true;
    }
}
