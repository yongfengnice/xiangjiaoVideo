package com.baby.app.modules.video.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.find.FindBean;
import com.android.baselibrary.service.bean.video.VideoCommentBean;
import com.android.baselibrary.service.bean.video.VideoDetailBean;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.modules.video.iview.IVideoView;

/**
 * Created by yongqianggeng on 2018/10/17.
 */

public class VideoPresenter extends BasePresenter {

    private IVideoView mIVideoView;

    public VideoPresenter(IVideoView mIVideoView) {
        this.mIVideoView = mIVideoView;
    }

    @Override
    protected BaseView getView() {
        return mIVideoView;
    }

    public void fetchDeitalData(String videoId){
        requestDateNew(NetService.getInstance().getVideoDetail(videoId), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                VideoDetailBean mVideoDetailBean = (VideoDetailBean)obj;
                mIVideoView.refreshDetail(mVideoDetailBean);
            }

            @Override
            public void onFaild(Object obj) {
                VideoDetailBean mVideoDetailBean = (VideoDetailBean)obj;
                if (mVideoDetailBean.getRetCode().equals("-2")) {
                    mIVideoView.dontPlayVideo();
                }
            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    public void usedViewOrCacheNum(String type,String videoId){
        requestDateNew(NetService.getInstance().usedViewOrCacheNum(type,videoId), Constants.DIALOG_LOADING, new BaseCallBack() {
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




    public void setCareTimes(String videoId, final String careType){
        requestDateNew(NetService.getInstance().setCareTimes(videoId,careType), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean baseBean = (BaseBean)obj;
                if (careType.equals("1")) {
                    ToastUtil.showToast("点赞成功");
                } else {
                    ToastUtil.showToast("提交成功");
                }
            }
            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    public void saveloseVideo(String videoId){
        requestDateNew(NetService.getInstance().saveloseVideo(videoId), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean baseBean = (BaseBean)obj;
                ToastUtil.showToast(baseBean.getRetMsg());
            }
            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    public void setCareHistory(String videoId){
        requestDateNew(NetService.getInstance().setCareHistory(videoId), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean baseBean = (BaseBean)obj;
                ToastUtil.showToast("喜欢成功");
            }
            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    public void getVideoCommon(String videoId){
        requestDateNew(NetService.getInstance().getVideoCommon(videoId), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                VideoCommentBean videoCommentBean = (VideoCommentBean)obj;
                mIVideoView.refreshComment(videoCommentBean);
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
        if (bean instanceof VideoDetailBean) {
            return;
        }
        super.showMsgFailed(bean);
    }

    public void getCacheNum(String videoId){
        requestDateNew(NetService.getInstance().getCacheNum(videoId), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean baseBean = (BaseBean)obj;
                mIVideoView.isCanDown();
            }
            @Override
            public void onFaild(Object obj) {
                mIVideoView.onCacheFailed();
            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }
}
