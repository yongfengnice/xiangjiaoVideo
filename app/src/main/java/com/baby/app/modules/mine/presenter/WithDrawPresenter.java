package com.baby.app.modules.mine.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.mine.WithDrawBean;
import com.baby.app.modules.mine.view.IWithDrawView;

/**
 * Created by yongqianggeng on 2018/12/4.
 */

public class WithDrawPresenter extends BasePresenter {

    private IWithDrawView iWithDrawView;

    public WithDrawPresenter(IWithDrawView iWithDrawView) {

        this.iWithDrawView = iWithDrawView;
    }

    @Override
    protected BaseView getView() {
        return iWithDrawView;
    }

    public void fetchData() {
        requestDateNew(NetService.getInstance().getApplyMemberCron(), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                WithDrawBean bean = (WithDrawBean) obj;
                iWithDrawView.callBack(bean);
            }
            @Override
            public void onFaild(Object obj) {
            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    public void withDraw(String name,String cardNo,String price) {
        requestDateNew(NetService.getInstance().submitApplyCron(name,cardNo,price), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean bean = (BaseBean) obj;
                iWithDrawView.withDrawSuccess();
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
