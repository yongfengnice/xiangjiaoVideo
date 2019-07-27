package com.baby.app.login.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.baby.app.login.view.LoginView;

/**
 * Created by yongqianggeng on 2018/10/8.
 */

public class LoginPresenter extends BasePresenter {

    private LoginView mLoginView;

    public LoginPresenter(LoginView loginView){
        this.mLoginView = loginView;
    }

    @Override
    protected BaseView getView() {
        return mLoginView;
    }

    public void login(String tel, String pw, final boolean isMemeory){
        requestDateNew(NetService.getInstance().login(tel,pw), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                LoginBean bean = (LoginBean) obj;
                if (isMemeory) {
                    UserStorage.getInstance().doLogin(bean);
                } else {
                    UserStorage.getInstance().noMemerylogin(bean);
                }
                mLoginView.loginSuccess();
            }
            @Override
            public void onFaild(Object obj) {
            }

            @Override
            public void onNetWorkError(String errorMsg) {
            }
        });
    }
}
