package com.android.baselibrary.usermanger;

/**
 * Created by gyq on 2016/8/8.
 * 退出登录事件
 */
public class LogoutEvent {

    public static final String TAG = "LogoutEvent";

    public LogoutEvent(boolean isQuit) {
        setIsQuit(isQuit);
    }

    private boolean isQuit;

    public boolean getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(boolean isQuit) {
        this.isQuit = isQuit;
    }
}
