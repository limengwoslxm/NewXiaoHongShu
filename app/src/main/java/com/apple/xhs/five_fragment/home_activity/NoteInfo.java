package com.apple.xhs.five_fragment.home_activity;

import com.bean.MyUser;
import com.bean.Note;

/**
 * Created by limeng on 2017/7/28.
 */

public class NoteInfo {
    Note note;
    MyUser user;
    public NoteInfo(Note note,MyUser user){
        this.note = note;
        this.user = user;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
