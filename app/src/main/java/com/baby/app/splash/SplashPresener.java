package com.baby.app.splash;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.widget.toast.ToastUtil;

/**
 * Created by yongqianggeng on 2018/10/8.
 * 启动页面
 */

public class SplashPresener extends BasePresenter {

    private SplashView mSplashView;

    public SplashPresener(SplashView splashView) {
        this.mSplashView = splashView;
    }

    @Override
    protected BaseView getView() {
        return this.mSplashView;
    }

    public void fetchDeviceInfo(){
        if (UserStorage.getInstance().isLogin() && UserStorage.getInstance().getUserType() == UserType.MARK_USER) {
            requestDateNew(NetService.getInstance().deviceInfo2(UserStorage.getInstance().getToken()), Constants.DIALOG_LOADING, new BaseCallBack() {
                @Override
                public void onSuccess(Object obj) {
                    LoginBean bean = (LoginBean) obj;
                    UserStorage.getInstance().touristLogin(bean);
                    mSplashView.fetchDeviceInfo(bean);
                }
                @Override
                public void onFaild(Object obj) {
                    mSplashView.faied();
                }

                @Override
                public void onNetWorkError(String errorMsg) {
                    mSplashView.faied();
                }
            });


        } else {
            requestDateNew(NetService.getInstance().deviceInfo(), Constants.DIALOG_LOADING, new BaseCallBack() {
                @Override
                public void onSuccess(Object obj) {
                    LoginBean bean = (LoginBean) obj;
                    UserStorage.getInstance().touristLogin(bean);
                    mSplashView.fetchDeviceInfo(bean);
                }
                @Override
                public void onFaild(Object obj) {
                    mSplashView.faied();
                }

                @Override
                public void onNetWorkError(String errorMsg) {
                    mSplashView.faied();
                }
            });
        }

    }

    public void fetchOutInfo(){
            requestDateNew(NetService.getInstance().deviceInfo(), Constants.DIALOG_LOADING, new BaseCallBack() {
                @Override
                public void onSuccess(Object obj) {
                    LoginBean bean = (LoginBean) obj;
                    UserStorage.getInstance().touristLogin(bean);
                    mSplashView.fetchDeviceInfo(bean);
                }
                @Override
                public void onFaild(Object obj) {
                    mSplashView.faied();
                }

                @Override
                public void onNetWorkError(String errorMsg) {
                    mSplashView.faied();
                }
            });

    }
}
