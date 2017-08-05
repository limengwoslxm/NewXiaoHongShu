package com.apple.xhs.five_fragment.mine_activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apple.xhs.Login;
import com.apple.xhs.R;
import com.apple.xhs.five_fragment.mine_activity.MineShowGuanzhu;
import com.apple.xhs.five_fragment.mine_activity.MineUserInfoSetting;
import com.apple.xhs.note.SelfNoteScan;
import com.bean.MyUser;
import com.bean.Note;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.process.CircleImageProcessor;
import me.xiaopan.sketch.request.DisplayOptions;

import static com.data.AddDataBmob.compressBitmap;

/**
 * Created by limeng on 2017/7/22.
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    SketchImageView head_icon;
    TextView nickname;
    TextView guanzhu,fans,mynotes,mylikes;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    guanzhu.setText(msg.arg1 + "");
                    break;
                case 2:
                    fans.setText(msg.arg1 + "");
                    break;
                case 3:
                    mynotes.setText(msg.arg1 + "");
                    break;
                case 4:
                    mylikes.setText(msg.arg1 + "");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_layout,container,false);
        view.findViewById(R.id.mine_exit_account).setOnClickListener(this);
        view.findViewById(R.id.myselfnote).setOnClickListener(this);
        view.findViewById(R.id.ge).setOnClickListener(this);
        view.findViewById(R.id.me_guanzhu).setOnClickListener(this);
        view.findViewById(R.id.me_guanzhu1).setOnClickListener(this);
        view.findViewById(R.id.me_head).setOnClickListener(this);
        head_icon = view.findViewById(R.id.img_me_user_head);
        nickname = view.findViewById(R.id.me_nickname);
        guanzhu = view.findViewById(R.id.me_guanzhu1);
        fans = view.findViewById(R.id.me_fans);
        mynotes = view.findViewById(R.id.me_minenote);
        mylikes = view.findViewById(R.id.me_likes);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        DisplayOptions displayOptions = new DisplayOptions();
        displayOptions.setImageProcessor(CircleImageProcessor.getInstance());
        head_icon.setOptions(displayOptions);
        if(myUser.getHead()!=null){
            head_icon.displayImage(myUser.getHead().getUrl());
        }
        if(myUser.getNickname() != null){
            nickname.setText(myUser.getNickname());
        }

        selectFour();
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
                break;
            case R.id.myselfnote:
                Intent intent = new Intent(getActivity(), SelfNoteScan.class);
                intent.putExtra("userselfnote",BmobUser.getCurrentUser(MyUser.class));
                startActivity(intent);
                break;
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
                String tempPath = Environment.getExternalStorageDirectory().getPath()
                        + "/XHS/temp/" + System.currentTimeMillis() + ".jpg";
                compressBitmap(img_url,tempPath);
                Log.i("bmob","头像图片压缩成功，地址：" + tempPath);

                head_icon.displayImage(tempPath);
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

    //关注、粉丝、发布、收藏
    private void selectFour(){
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);

        BmobQuery<MyUser> query1 = new BmobQuery<MyUser>();
        query1.addWhereRelatedTo("attention",new BmobPointer(myUser));
        query1.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e==null){
                    Log.i("bmob","查询关注列表成功，数目为：" + list.size());
                    if (list!=null){
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.arg1 = list.size();
                        handler.sendMessage(message);
                    }
                }else {
                    Log.i("bmob","查询关注列表失败：" + e.getMessage() + e.getErrorCode());
                }
            }
        });

        BmobQuery<MyUser> query2 = new BmobQuery<MyUser>();
        query2.addWhereRelatedTo("fans",new BmobPointer(myUser));
        query2.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e==null){
                    Log.i("bmob","查询粉丝列表成功，数目为：" + list.size());
                    if (list!=null){
                        Message message = handler.obtainMessage();
                        message.what = 2;
                        message.arg1 = list.size();
                        handler.sendMessage(message);
                    }
                }else {
                    Log.i("bmob","查询粉丝列表失败：" + e.getMessage() + e.getErrorCode());
                }
            }
        });

        BmobQuery<Note> query3 = new BmobQuery<Note>();
        query3.addWhereEqualTo("author",myUser);
        query3.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询个人笔记列表成功，数目为：" + list.size());
                    if(list!=null){
                        Message message = handler.obtainMessage();
                        message.what = 3;
                        message.arg1 = list.size();
                        handler.sendMessage(message);
                    }
                }else{
                    Log.i("bmob","查询个人笔记列表失败：" + e.getMessage() + e.getErrorCode());
                }
            }
        });

        BmobQuery<Note> query4 = new BmobQuery<Note>();
        query4.addWhereRelatedTo("likes",new BmobPointer(myUser));
        query4.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e==null){
                    Log.i("bmob","查询收藏列表成功，数目为：" + list.size());
                    if (list!=null){
                        Message message = handler.obtainMessage();
                        message.what = 4;
                        message.arg1 = list.size();
                        handler.sendMessage(message);
                    }
                }else {
                    Log.i("bmob","查询收藏列表失败：" + e.getMessage() + e.getErrorCode());
                }
            }
        });
    }
}
