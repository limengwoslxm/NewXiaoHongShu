package com.apple.xhs.five_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apple.xhs.R;
import com.apple.xhs.five_fragment.search_activity.SearchAdd;

/**
 * Created by limeng on 2017/7/22.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    ImageView addFriend;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_layout,container,false);
        addFriend = view.findViewById(R.id.search_add_friend);
        initListener(view);
        return view;
    }

    private void initListener(View view) {
        addFriend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_add_friend:
                startActivity(new Intent(getActivity(), SearchAdd.class));
                break;
        }
    }
}
