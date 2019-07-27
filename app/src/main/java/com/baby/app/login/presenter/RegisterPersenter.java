package com.baby.app.login.presenter;

import android.os.Handler;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.login.view.RegisterView;

/**
 * Created by yongqianggeng on 2018/10/8.
 *
 */

public class RegisterPersenter extends BasePresenter {

    private RegisterView mRegisterView;

    private Runnable updateTimeRunnable;
    private Handler mHandler;


    public RegisterPersenter(RegisterView registerView) {
        this.mRegisterView = registerView;
    }

    @Override
    protected BaseView getView() {
        return mRegisterView;
    }

    public void fetchCode(String tel,String type){
        requestDateNew(NetService.getInstance().getSmsCode(tel,type), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean bean = (BaseBean) obj;
                ToastUtil.showToast(bean.getRetMsg());
            }
            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {
            }
        });
    }

    public void register(String tel,String smsCode,String pwd,String extensionCode){
        requestDateNew(NetService.getInstance().regedit(tel,smsCode,pwd,extensionCode), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                LoginBean bean = (LoginBean) obj;
                UserStorage.getInstance().doLogin(bean);

                ToastUtil.showToast(bean.getRetMsg());
                mRegisterView.registerSuccess();
            }
            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {
            }
        });
    }

    public void loseTel(String tel,String smsCode,String pwd){
        requestDateNew(NetService.getInstance().loseTel(tel,smsCode,pwd), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                LoginBean bean = (LoginBean) obj;
                UserStorage.getInstance().doLogin(bean);

                ToastUtil.showToast(bean.getRetMsg());
                mRegisterView.registerSuccess();
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
