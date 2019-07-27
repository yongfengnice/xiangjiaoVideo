package com.android.baselibrary.util;

/**
 * Created by gyq on 2016/8/8.
 */
public class FastClickUtil {

    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
