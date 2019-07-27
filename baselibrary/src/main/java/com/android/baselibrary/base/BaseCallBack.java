package com.android.baselibrary.base;

/**
 * Created by gyq on 2016/7/22.
 */
public interface BaseCallBack {

    public void onSuccess(Object obj);
    public void onFaild(Object obj);
    public void onNetWorkError(String errorMsg);
}
