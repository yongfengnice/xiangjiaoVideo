package com.android.baselibrary.usermanger;

/**
 * Created by yongqianggeng on 2017/12/8.
 */

public enum UserType {

    NOMAL_USER(0), //游客用户
    MARK_USER(1);  //认证用户

    private int type;
    private UserType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

