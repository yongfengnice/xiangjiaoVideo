package com.baby.app.modules.mine.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.baby.app.modules.mine.view.IRechargeView;

/**
 * Created by yongqianggeng on 2018/12/4.
 */

public class RechargePresenter extends BasePresenter {

    private IRechargeView iRechargeView;

    public RechargePresenter(IRechargeView iRechargeView) {
        this.iRechargeView = iRechargeView;
    }

    @Override
    protected BaseView getView() {
        return iRechargeView;
    }


    public void recharge(String code) {
        requestDateNew(NetService.getInstance().useRechargeCode(code), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean bean = (BaseBean) obj;
                iRechargeView.rechargeSuccess();
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
