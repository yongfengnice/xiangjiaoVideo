package com.baby.app.modules.search.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.search.SearchBean;
import com.baby.app.modules.search.view.SearchView;

/**
 * Created by yongqianggeng on 2018/10/16.
 */

public class SearchPresenter extends BasePresenter {

    private SearchView mSearchView;

    public SearchPresenter(SearchView searchView) {
        this.mSearchView = searchView;
    }

    @Override
    protected BaseView getView() {
        return mSearchView;
    }

    /**
    * 热门搜索
    * */
    public void fetchHotSearchList() {
        requestDateNew(NetService.getInstance().searchSearchName(), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                SearchBean searchBean = (SearchBean)obj;
                mSearchView.refersh(searchBean);
            }
            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }

    /**
     * 搜索
     * */
    public void selectOPenVideo(String searchName) {
        requestDateNew(NetService.getInstance().selectOPenVideo(searchName), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                DetailListBean detailListBean = (DetailListBean)obj;
                mSearchView.refreshList(detailListBean);
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
