package com.baby.app.modules.home.persenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.home.StarDataBean;
import com.android.baselibrary.service.request.StarListRequest;
import com.baby.app.modules.home.view.StarListView;

import java.util.HashMap;

/**
 * Created by yongqianggeng on 2018/10/14.
 * 明星列表
 */

public class StarListPresenter extends BasePresenter {

    private StarListView mStarListView;

    public StarListPresenter(StarListView starListView) {
        this.mStarListView = starListView;
    }

    @Override
    protected BaseView getView() {
        return mStarListView;
    }

    public void fetchData(StarListRequest request) {

        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("pageNum",String.valueOf(request.getPageNum()));
        if (request.getCup() != null && request.getCup().length() > 0) {
            paramsMap.put("cup",request.getCup());
        }
        if (request.getNewVideo().equals("1")) {
            paramsMap.put("newVideo","1");
        }
        if (request.getVideoNum().equals("1")) {
            paramsMap.put("videoNum","1");
        }

        requestDateNew(NetService.getInstance().getStarPage(paramsMap), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                StarDataBean bean = (StarDataBean) obj;
                mStarListView.refresh(bean);
            }
            @Override
            public void onFaild(Object obj) {
                mStarListView.showNetError();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mStarListView.showNetError();
            }
        });
    }
}
