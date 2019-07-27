package com.android.baselibrary.base;

import android.os.Bundle;

/**
 * Created by gyq on 2016/7/6.
 */
public interface BaseView {

    void showDialogLoading();

    void showDialogLoading(String msg);

    void hideDialogLoading();

    void showPageLoading();

    void hidePageLoading();

    void refreshView();

    void showNetError();

    void showNetError(String msg, int icon);

    void showEmptyView(String msg, int icon);

    void showToast(String msg);// toast现实

    void showRequestOutData();

    void gotoLogin();

    void openActivity(Class<?> pClass);

    void openActivity(Class<?> pClass, Bundle pBundle);

    void showTopTip(String text, int res);

}
