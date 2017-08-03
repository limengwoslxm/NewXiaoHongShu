package com.apple.xhs.custom_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apple.xhs.R;

import me.xiaopan.sketch.SketchImageView;

/**
 * Created by limeng on 2017/8/3.
 */

public class SelfNoteCard extends LinearLayout {
    TextView selfNoteTitle;
    TextView selfNoteShoucang;
    TextView selfNoteDate;
    SketchImageView sketchImageOne;
    SketchImageView sketchImageTwo;
    SketchImageView sketchImageThree;
    SketchImageView sketchImageFour;
    public SelfNoteCard(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.note_card_custom,this);
        selfNoteTitle = findViewById(R.id.yourselfnotetitle);
        selfNoteShoucang = findViewById(R.id.yourselfnoteshoucang);
        selfNoteDate = findViewById(R.id.yourselfnotedate);
        sketchImageOne = findViewById(R.id.yourselfnoteimg1);
        sketchImageTwo = findViewById(R.id.yourselfnoteimg2);
        sketchImageThree = findViewById(R.id.yourselfnoteimg3);
        sketchImageFour = findViewById(R.id.yourselfnoteimg4);
    }

    public void setSelfNoteTitle(String title) {
        selfNoteTitle.setText(title);
    }

    public void setSelfNoteShoucang(String sc) {
        selfNoteShoucang.setText(sc);
    }

    public void setSelfNoteDate(String date) {
        selfNoteDate.setText(date);
    }

    public void setSketchImageOne(String url) {
        sketchImageOne.displayImage(url);
    }

    public void setSketchImageTwo(String url) {
        sketchImageTwo.displayImage(url);
    }

    public void setSketchImageThree(String url) {
        sketchImageThree.displayImage(url);
    }

    public void setSketchImageFour(String url) {
        sketchImageFour.displayImage(url);
    }
}
