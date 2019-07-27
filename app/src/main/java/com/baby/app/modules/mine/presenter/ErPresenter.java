package com.baby.app.modules.mine.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.mine.ExtensionBean;
import com.baby.app.modules.mine.view.IErView;

/**
 * Created by yongqianggeng on 2018/10/19.
 */

public class ErPresenter extends BasePresenter {

    private IErView mIErView;

    public ErPresenter(IErView mIErView) {
        this.mIErView = mIErView;
    }

    @Override
    protected BaseView getView() {
        return mIErView;
    }

    public void fetchData(){
        requestDateNew(NetService.getInstance().getExtensionCode(), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                ExtensionBean bean = (ExtensionBean) obj;
                mIErView.refreshErUrl(bean.getExtensionContext(),bean.getExtensionCode());
            }
            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    public void saveQrcode() {
        requestDateNew(NetService.getInstance().saveQrcode(), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {

            }
            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    @Override
    protected void showMsgFailed(BaseBean bean) {
//        super.showMsgFailed(bean);
    }
}
