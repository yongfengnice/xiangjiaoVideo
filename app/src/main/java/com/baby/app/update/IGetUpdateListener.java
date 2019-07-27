package com.baby.app.update;

/**
 * Created by saky on 2017/7/6.
 *
 */

public interface IGetUpdateListener {

    public enum DIALOG_FLAG
    {
        UPDATE, AD
    };

    /***
     * flag 用来区分是更新，广告等弹窗
     * @param flag
     */

    public void getUpdataComplete(DIALOG_FLAG flag);
}
