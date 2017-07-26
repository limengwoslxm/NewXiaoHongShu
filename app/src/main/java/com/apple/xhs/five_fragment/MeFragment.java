package com.apple.xhs.five_fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.apple.initbmob.InitBmob;
import com.apple.xhs.Login;
import com.apple.xhs.R;
import com.apple.xhs.five_fragment.mine_activity.MineUserInfoSetting;
import com.base.BaseCache;
import com.bean.MyUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by limeng on 2017/7/22.
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    ImageView head_icon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_layout,container,false);
        view.findViewById(R.id.mine_exit_account).setOnClickListener(this);
        view.findViewById(R.id.ge).setOnClickListener(this);
        head_icon = view.findViewById(R.id.img_me_user_head);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshHead();
    }

    private void refreshHead() {
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if(myUser.getHead()!=null){
            if (InitBmob.getLocal_head_url()==null){
                String url = myUser.getHead().getUrl();
                ImageLoader loader = new ImageLoader(InitBmob.getRequestQueue(), new BaseCache());
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(head_icon,R.drawable.xy_walkthroughs_account,R.drawable.xy_walkthroughs_account);
                loader.get(url,listener);
            }else {
                String url = InitBmob.getLocal_head_url();
                Bitmap photo = BitmapFactory.decodeFile(url);
                head_icon.setImageBitmap(photo);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_exit_account:
                popExitAialog();
                break;
            case R.id.ge:
                startActivity(new Intent(getActivity(), MineUserInfoSetting.class));
        }
    }
    //退出账户的方法
    private void popExitAialog() {
        new AlertDialog.Builder(getActivity()).setTitle("登出")
                .setMessage("确定登出？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //确定退出
                        BmobUser.logOut();   //清除缓存用户对象
                        startActivity(new Intent(getActivity(), Login.class));
                        Toast.makeText(getContext(),"退出",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}
