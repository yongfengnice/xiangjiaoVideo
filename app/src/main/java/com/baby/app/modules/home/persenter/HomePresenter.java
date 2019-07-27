package com.baby.app.modules.home.persenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.home.HomeDataBean;
import com.baby.app.modules.home.view.HomeView;

/**
 * Created by yongqianggeng on 2018/10/8.
 */

public class HomePresenter extends BasePresenter {

    private HomeView mHomeView;

    public HomePresenter(HomeView homeView){
        this.mHomeView = homeView;
    }

    @Override
    protected BaseView getView() {
        return mHomeView;
    }

    public void fetchHomeData() {
        requestDateNew(NetService.getInstance().indexInfo(), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                HomeDataBean bean = (HomeDataBean) obj;
                mHomeView.refreshHomeData(bean);
            }
            @Override
            public void onFaild(Object obj) {
                mHomeView.showNetError();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mHomeView.showNetError();
            }
        });
    }

    public void clickAd(String id){
        requestDateNew(NetService.getInstance().clickAd(id), Constants.DIALOG_LOADING, new BaseCallBack() {
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

}
