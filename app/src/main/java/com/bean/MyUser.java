package com.bean;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by xiong on 2017/7/22.
 */

public class MyUser extends BmobUser {
    private String nickname;
    private BmobFile head;
    private Boolean sex;
    private String birthday;
    private String signature;
    private String CopyId;
    private Boolean Change;
    private List<Map<Integer,String>> skin;
    private String pregnant;
    private BmobRelation attention;
    private BmobRelation fans;
    private String address;
    private BmobRelation likes;
    private List<String> history;

    public MyUser() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BmobFile getHead() {
        return head;
    }

    public void setHead(BmobFile head) {
        this.head = head;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public String getCopyId() {
        return CopyId;
    }

    public void setCopyId(String copyId) {
        CopyId = copyId;
    }

    public Boolean getChange() {
        return Change;
    }

    public void setChange(Boolean change) {
        Change = change;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<Map<Integer,String>> getSkin() {
        return skin;
    }

    public void setSkin(List<Map<Integer,String>> skin) {
        this.skin = skin;
    }

    public String getPregnant() {
        return pregnant;
    }

    public void setPregnant(String pregnant) {
        this.pregnant = pregnant;
    }

    public BmobRelation getAttention() {
        return attention;
    }

    public void setAttention(BmobRelation attention) {
        this.attention = attention;
    }

    public BmobRelation getFans() {
        return fans;
    }

    public void setFans(BmobRelation fans) {
        this.fans = fans;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }
}
