package com.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by xiong on 2017/7/24.
 */

public class Note extends BmobObject{

    private List<BmobFile> image;
    private String title;
    private String content;
    private MyUser author;
    private Integer up;

    public Note() {
    }

    public List<BmobFile> getImage() {
        return image;
    }

    public void setImage(List<BmobFile> image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }
}
