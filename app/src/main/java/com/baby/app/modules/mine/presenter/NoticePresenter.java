package com.baby.app.modules.mine.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.mine.NotificationBean;
import com.android.baselibrary.service.bean.user.ProtocolBean;
import com.baby.app.modules.mine.view.INoticeView;

public class NoticePresenter extends BasePresenter {

    private INoticeView iNoticeView;

    public NoticePresenter(INoticeView iNoticeView) {
        this.iNoticeView = iNoticeView;
    }

    @Override
    protected BaseView getView() {
        return iNoticeView;
    }

    public void fetchData() {
        requestDateNew(NetService.getInstance().getNoticeList(1), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                NotificationBean bean = (NotificationBean) obj;
                iNoticeView.refresh(bean);
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
