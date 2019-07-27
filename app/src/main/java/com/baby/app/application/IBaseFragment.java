package com.baby.app.application;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.android.baselibrary.AppCommon;
import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BaseFragment;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.page.CommonWebViewActivity;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.home.HomeBannerBean;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.service.bean.video.VideoInComeBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.login.LoginActivity;
import com.baby.app.modules.channel.page.TagListActivity;
import com.baby.app.modules.mine.page.ErActivity;
import com.baby.app.modules.mine.page.VipActivity;
import com.baby.app.modules.video.page.VideoActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/9.
 */

public class IBaseFragment extends BaseFragment {

    private BasePresenter mBasePresenter;

    @Override
    protected int getLayoutView() {
        return 0;
    }

    @Override
    public void initUiAndListener(View view) {

    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {

    }

    public void reStartNamalLogin(){

    }

    public void jumpToVideo(int id,String name,String url){
        VideoInComeBean videoInComeBean = new VideoInComeBean();
        videoInComeBean.setId(id);
        videoInComeBean.setVideoName(name);
        videoInComeBean.setVideoUrl(url);
        Bundle bundle = new Bundle();
        bundle.putSerializable(VideoActivity.VIDEO_KEY, (Serializable) videoInComeBean);
        openActivity(VideoActivity.class,bundle);
    }

    public void jumpToTagActivity(HomeBannerBean homeBannerBean){
        if (homeBannerBean.getLinkType() == 1) {
            //TODO:这里需要判断类型
            Intent intent= new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(homeBannerBean.getLinkUrl());
            intent.setData(content_url);
            startActivity(intent);
        } else {
            switch (homeBannerBean.getLinkType()) {
                case 2: //内部影片
                    jumpToVideo(Integer.parseInt(homeBannerBean.getLinkUrl()),"",null);
                    break;
                case 3:
                    openActivity(VipActivity.class);
                    break;
                case 4:
                    openActivity(ErActivity.class);
                    break;
                case 5: {
                    if (homeBannerBean.getTagName()!=null && homeBannerBean.getTagName().length() > 0) {
                        ChannelTagBean channelTagBean = new ChannelTagBean();
                        channelTagBean.setId(Integer.parseInt(homeBannerBean.getTagId()));
                        channelTagBean.setName(homeBannerBean.getTagName());
                        Bundle bundle = new Bundle();
                        List<ChannelTagBean> list = new ArrayList<>();
                        list.add(channelTagBean);
                        bundle.putSerializable(TagListActivity.TAG_LIST_KEY, (Serializable) list);
                        openActivity(TagListActivity.class,bundle);
                    } else {
                        openActivity(TagListActivity.class);
                    }

                }
                break;
            }
        }
    }

    @Override
    public void gotoLogin() {
        super.gotoLogin();
//
        AppCommon.showDialog(mContext, "提示", "您的登录已过期，是否重新登录", "确定", "取消", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                jumpToLogin();

            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //TODO:登录过期需要重新登录
                showDialogLoading();
                if (mBasePresenter == null) {
                    mBasePresenter = new BasePresenter() {
                        @Override
                        protected BaseView getView() {
                            return null;
                        }
                    };
                }
                mBasePresenter.requestDateNew(NetService.getInstance().deviceInfo(), "", new BaseCallBack() {
                    @Override
                    public void onSuccess(Object obj) {
                        hideDialogLoading();
                        LoginBean bean = (LoginBean) obj;
                        UserStorage.getInstance().touristLogin(bean);
                        reStartNamalLogin();
                    }

                    @Override
                    public void onFaild(Object obj) {
                        hideDialogLoading();
                    }

                    @Override
                    public void onNetWorkError(String errorMsg) {
                        hideDialogLoading();
                    }
                });
            }
        });
    }

    public void jumpToLogin(boolean isSplash) {
        if (isSplash) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(LoginActivity.SPLASH_KEY,true);
            openActivity(LoginActivity.class,bundle);
        } else {
            openActivity(LoginActivity.class);
        }
    }

    public void jumpToLogin() {
        openActivity(LoginActivity.class);
    }
}
