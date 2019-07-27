package com.baby.app.modules.mine.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.user.ProtocolBean;
import com.baby.app.modules.mine.view.ProtocolView;

/**
 * Created by yongqianggeng on 2018/10/13.
 */

public class ProtocolPresenter extends BasePresenter {

    private ProtocolView mProtocolView;

    public ProtocolPresenter(ProtocolView protocolView) {
        this.mProtocolView = protocolView;
    }

    @Override
    protected BaseView getView() {
        return this.mProtocolView;
    }

    public void fetchData() {
        requestDateNew(NetService.getInstance().getProtocol(), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                ProtocolBean bean = (ProtocolBean) obj;
                mProtocolView.refresh(bean);
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
