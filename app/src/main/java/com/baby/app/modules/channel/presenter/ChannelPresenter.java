package com.baby.app.modules.channel.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.channel.ChannelDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.channel.ChannelTagDataBean;
import com.android.baselibrary.service.bean.channel.TagClassBean;
import com.baby.app.modules.channel.view.ChannelView;

/**
 * Created by yongqianggeng on 2018/10/13.
 */

public class ChannelPresenter extends BasePresenter {

    private ChannelView mChannelView;

    public ChannelPresenter(ChannelView mChannelView) {
        this.mChannelView = mChannelView;
    }

    @Override
    protected BaseView getView() {
        return mChannelView;
    }

    public void fetchData() {
        requestDateNew(NetService.getInstance().channelInfo(), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                ChannelDataBean bean = (ChannelDataBean) obj;
                mChannelView.refresh(bean);
            }
            @Override
            public void onFaild(Object obj) {
                mChannelView.showNetError();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mChannelView.showNetError();
            }
        });
    }

    public void fetchTagClassData() {
        requestDateNew(NetService.getInstance().selectTagType(), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                TagClassBean bean = (TagClassBean) obj;
                mChannelView.refreshTagClass(bean);
            }
            @Override
            public void onFaild(Object obj) {
                mChannelView.showNetError();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mChannelView.showNetError();
            }
        });
    }

    public void selectTagsByType(int id) {
        requestDateNew(NetService.getInstance().selectTagsByType(id), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                ChannelTagDataBean bean = (ChannelTagDataBean) obj;
                mChannelView.refreshChannelTag(bean);
            }
            @Override
            public void onFaild(Object obj) {
                mChannelView.showNetError();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mChannelView.showNetError();
            }
        });
    }

}
