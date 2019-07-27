package com.baby.app.modules.mine.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.mine.PayBean;
import com.android.baselibrary.service.bean.mine.PayRecharegeBean;
import com.android.baselibrary.service.bean.mine.VipBean;
import com.android.baselibrary.service.bean.mine.WithDrawBean;
import com.baby.app.modules.mine.view.IVipView;

/**
 * Created by yongqianggeng on 2018/10/19.
 */

public class VipPresenter extends BasePresenter {

    private IVipView mIVipView;

    public VipPresenter(IVipView iVipView) {
        this.mIVipView = iVipView;
    }

    @Override
    protected BaseView getView() {
        return mIVipView;
    }


    public void fetchData() {
        requestDateNew(NetService.getInstance().getApplyMemberCron(), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                WithDrawBean bean = (WithDrawBean) obj;
                mIVipView.dataCallBack(bean);
            }
            @Override
            public void onFaild(Object obj) {
            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    public void fetchVipList(){
        requestDateNew(NetService.getInstance().getVipList(), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                VipBean bean = (VipBean) obj;
                mIVipView.refreshVip(bean);
            }
            @Override
            public void onFaild(Object obj) {
            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    public void fetchPayTypeList(){
        requestDateNew(NetService.getInstance().getPayType(), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                PayBean bean = (PayBean) obj;
                mIVipView.refreshPayType(bean);
            }
            @Override
            public void onFaild(Object obj) {
            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    public void payRecharge(String vipId,String payType,String selectType){
        requestDateNoLog(NetService.getInstance().payRecharge(vipId,payType,selectType), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                PayRecharegeBean payRecharegeBean = (PayRecharegeBean) obj;
                mIVipView.refreshPayRecharge(payRecharegeBean);
            }
            @Override
            public void onFaild(Object obj) {
            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    public void getPayStatus(String tradeNo){
        requestDateNew(NetService.getInstance().getPayStatus(tradeNo), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean payRecharegeBean = (BaseBean) obj;
                fetchVipList();
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
