package com.baby.app.modules.mine.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.mine.MyPromoteBean;
import com.baby.app.modules.mine.view.IMyPromoteView;

/**
 * Created by yongqianggeng on 2018/10/20.
 */

public class MyPromotePresenter extends BasePresenter {

    private IMyPromoteView mIMyPromoteView;

    public MyPromotePresenter(IMyPromoteView mIMyPromoteView) {
        this.mIMyPromoteView = mIMyPromoteView;
    }

    @Override
    protected BaseView getView() {
        return mIMyPromoteView;
    }

    public void fetchData(){
        requestDateNew(NetService.getInstance().getExtensionHistory(), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                MyPromoteBean bean = (MyPromoteBean) obj;
                mIMyPromoteView.refreshMyPromote(bean);
            }
            @Override
            public void onFaild(Object obj) {
                mIMyPromoteView.showNetError();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mIMyPromoteView.showNetError();
            }
        });
    }

    public void fetchMyincome() {
        requestDateNew(NetService.getInstance().myincome(), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                MyPromoteBean bean = (MyPromoteBean) obj;
                mIMyPromoteView.refreshMyPromote(bean);
            }
            @Override
            public void onFaild(Object obj) {
                mIMyPromoteView.showNetError();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mIMyPromoteView.showNetError();
            }
        });

    }
}
