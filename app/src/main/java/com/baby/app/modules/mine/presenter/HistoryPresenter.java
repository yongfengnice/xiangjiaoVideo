package com.baby.app.modules.mine.presenter;

import android.os.Handler;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.mine.MyHistoryBean;
import com.baby.app.modules.mine.view.IHistoryView;

/**
 * Created by yongqianggeng on 2018/10/22.
 */

public class HistoryPresenter extends BasePresenter {

    private IHistoryView mIHistoryView;

    private int type;

    public HistoryPresenter(IHistoryView iHistoryView) {
        this.mIHistoryView = iHistoryView;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    protected BaseView getView() {
        return mIHistoryView;
    }

    public void fetchData(){
        if (type == 0) {
            requestDateNew(NetService.getInstance().getMemberCareHistoryMore(), Constants.DIALOG_LOADING, new BaseCallBack() {
                @Override
                public void onSuccess(Object obj) {
                    MyHistoryBean bean = (MyHistoryBean) obj;
                    mIHistoryView.refresh(bean);
                }
                @Override
                public void onFaild(Object obj) {

                }

                @Override
                public void onNetWorkError(String errorMsg) {

                }
            });
        } else {
            requestDateNew(NetService.getInstance().getMemberViewHistoryMore(), Constants.DIALOG_LOADING, new BaseCallBack() {
                @Override
                public void onSuccess(Object obj) {
                    MyHistoryBean bean = (MyHistoryBean) obj;
                    mIHistoryView.refresh(bean);
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

    public void delete(final String ids,final int count){
        mIHistoryView.showDialogLoading("正在删除...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (type == 0) {

                    requestDateNew(NetService.getInstance().deleteCareHistory(ids), "", new BaseCallBack() {
                        @Override
                        public void onSuccess(Object obj) {
                            BaseBean bean = (BaseBean) obj;
                            mIHistoryView.deleteSuccess(count);
                        }
                        @Override
                        public void onFaild(Object obj) {

                        }

                        @Override
                        public void onNetWorkError(String errorMsg) {

                        }
                    });
                } else {
                    requestDateNew(NetService.getInstance().deleteViewHistory(ids), "", new BaseCallBack() {
                        @Override
                        public void onSuccess(Object obj) {
                            BaseBean bean = (BaseBean) obj;
                            mIHistoryView.deleteSuccess(count);
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
        },100);

    }
}
