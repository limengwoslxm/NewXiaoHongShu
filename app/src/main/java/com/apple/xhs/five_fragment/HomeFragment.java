package com.apple.xhs.five_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.xhs.R;
import com.apple.xhs.five_fragment.home_activity.ScrollViewActivity;

import butterknife.ButterKnife;

/**
 * Created by limeng on 2017/7/22.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout,container,false);
        view.findViewById(R.id.home_scrollview_showMore).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_scrollview_showMore:
                startActivity(new Intent(getActivity(), ScrollViewActivity.class));
                break;
        }
    }
}
