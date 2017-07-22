package com.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by xiong on 2017/7/22.
 */

public class MyUser extends BmobUser {
    private String nickname;
    private String head;
    private Boolean sex;
    private String birthday;
    private String signature;

    public MyUser() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
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

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
