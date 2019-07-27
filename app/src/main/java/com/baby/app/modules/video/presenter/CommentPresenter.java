package com.baby.app.modules.video.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.baby.app.modules.video.iview.ICommentView;

/**
 * Created by yongqianggeng on 2018/10/18.
 */

public class CommentPresenter extends BasePresenter {

    private ICommentView mICommentView;

    public CommentPresenter(ICommentView commentView) {
        this.mICommentView = commentView;
    }

    @Override
    protected BaseView getView() {
        return mICommentView;
    }

    public void saveVideoCommon(String videoId,String content){
        requestDateNew(NetService.getInstance().saveVideoCommon(videoId,content), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean baseBean = (BaseBean)obj;
                mICommentView.commentSuccess();
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
