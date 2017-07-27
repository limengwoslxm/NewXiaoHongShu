package com.apple.xhs.five_fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apple.xhs.Login;
import com.apple.xhs.R;
import com.apple.xhs.five_fragment.mine_activity.MineShowGuanzhu;
import com.apple.xhs.five_fragment.mine_activity.MineUserInfoSetting;
import com.bean.MyUser;

import cn.bmob.v3.BmobUser;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.process.CircleImageProcessor;
import me.xiaopan.sketch.request.DisplayOptions;

/**
 * Created by limeng on 2017/7/22.
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    SketchImageView head_icon;
    TextView nickname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_layout,container,false);
        view.findViewById(R.id.mine_exit_account).setOnClickListener(this);
        view.findViewById(R.id.ge).setOnClickListener(this);
        view.findViewById(R.id.me_guanzhu).setOnClickListener(this);
        view.findViewById(R.id.me_guanzhu1).setOnClickListener(this);
        view.findViewById(R.id.me_head).setOnClickListener(this);
        head_icon = view.findViewById(R.id.img_me_user_head);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        DisplayOptions displayOptions = new DisplayOptions();
        displayOptions.setImageProcessor(CircleImageProcessor.getInstance());
        head_icon.setOptions(displayOptions);

        head_icon.displayImage(myUser.getHead().getUrl());
//        head_icon.setShowImageFromEnabled(true);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_exit_account:
                popExitAialog();
                break;
            case R.id.ge:
                startActivity(new Intent(getActivity(), MineUserInfoSetting.class));
                break;
            case R.id.me_guanzhu:
            case R.id.me_guanzhu1:
                startActivity(new Intent(getActivity(),MineShowGuanzhu.class));
                break;
            case R.id.me_head:
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");//图片
                startActivityForResult(galleryIntent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:
                break;
            case 1:
                Uri originalUri=data.getData();
                String []imgs1={MediaStore.Images.Media.DATA};//将图片URI转换成存储路径
                Cursor cursor=getActivity().managedQuery(originalUri, imgs1, null, null, null);
                int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String img_url=cursor.getString(index);

                head_icon.displayImage(img_url);
                break;
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
