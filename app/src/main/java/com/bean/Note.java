package com.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by xiong on 2017/7/24.
 */

public class Note extends BmobObject{

    private BmobFile image;
    private String title;
    private String content;
    private MyUser author;
    private BmobRelation likes;
    private BmobRelation style;

    public Note() {
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
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

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public BmobRelation getStyle() {
        return style;
    }

    public void setStyle(BmobRelation style) {
        this.style = style;
    }
}
