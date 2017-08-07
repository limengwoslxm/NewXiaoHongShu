package com.apple.xhs.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.apple.xhs.R;
import com.apple.xhs.custom_view.CommentModule;
import com.apple.xhs.custom_view.InfoSettingTitle;
import com.apple.xhs.custom_view.NoteDivideLine;
import com.base.BaseActivity;
import com.bean.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.xiaopan.sketch.process.CircleImageProcessor;
import me.xiaopan.sketch.request.DisplayOptions;

/**
 * Created by limeng on 2017/8/3.
 */

public class NoteAllPinglun extends BaseActivity {
    @BindView(R.id.allpingluntoolbar)
    InfoSettingTitle toolbar;
    @BindView(R.id.allpinglunparent)
    LinearLayout parent;
    DisplayOptions displayOptions;
    List<Comment> list = new ArrayList<>();
    @Override
    public int getContentViewId() {
        return R.layout.note_showallpinglun;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayOptions = new DisplayOptions();
        displayOptions.setImageProcessor(CircleImageProcessor.getInstance());
        initData();
        toolbar.setImgListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        list = (List<Comment>) intent.getSerializableExtra("allpinglun");
        toolbar.getTextView().setText(list.size()+"条评论");
        for(int i = 0;i < list.size() ;i++){
            Comment comment = list.get(i);
            String url = comment.getUser().getHead().getUrl();
            String nickname = comment.getUser().getNickname();
            String createdAt = comment.getCreatedAt();
            String content = comment.getContent();
            CommentModule module = new CommentModule(this,null);
            module.getHeadPic().setOptions(displayOptions);
            module.getHeadPic().displayImage(url);
            module.getUserContent().setText(content);
            module.getUserName().setText(nickname);
            module.getPushDate().setText(createdAt);
            NoteDivideLine line = new NoteDivideLine(this);
            parent.addView(module);
            parent.addView(line);
        }
    }
}
