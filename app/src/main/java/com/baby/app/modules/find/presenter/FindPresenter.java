package com.baby.app.modules.find.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.find.FindBean;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.modules.find.view.FindView;

/**
 * Created by yongqianggeng on 2018/10/9.
 */

public class FindPresenter extends BasePresenter {


    private FindView mFindView;

    public FindPresenter(FindView findView) {
        this.mFindView = findView;
    }

    @Override
    protected BaseView getView() {
        return mFindView;
    }

    public void fetchData(int pageNum) {
        requestDateNew(NetService.getInstance().getFindVideo(pageNum), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                FindBean bean = (FindBean) obj;
                mFindView.refreshList(bean);
            }
            @Override
            public void onFaild(Object obj) {
                mFindView.showNetError();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mFindView.showNetError();
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

    public void getCacheNum(final FindBean.Data findBean){
        requestDateNew(NetService.getInstance().getCacheNum(String.valueOf(findBean.getId())), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean baseBean = (BaseBean)obj;
               mFindView.isCanDown(findBean);
            }
            @Override
            public void onFaild(Object obj) {

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

}
