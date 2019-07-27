package com.baby.app.splash;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.user.LoginBean;

/**
 * Created by yongqianggeng on 2018/10/8.
 */

public interface SplashView extends BaseView {
    void fetchDeviceInfo(LoginBean loginBean);
    void faied();
}
