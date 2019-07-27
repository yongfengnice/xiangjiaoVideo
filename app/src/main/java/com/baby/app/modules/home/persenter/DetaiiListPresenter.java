package com.baby.app.modules.home.persenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.request.DetailListRequest;
import com.baby.app.modules.home.view.DetailListView;

import java.util.HashMap;

/**
 * Created by yongqianggeng on 2018/10/13.
 */

public class DetaiiListPresenter extends BasePresenter {

    private DetailListView mDetailListView;

    public DetaiiListPresenter(DetailListView listView) {
        this.mDetailListView = listView;
    }

    @Override
    protected BaseView getView() {
        return mDetailListView;
    }


    /**
     * 根据明星ID、分类、标签获取影片列表-S
     * mostCare 最多喜欢
     * newVideo 最新播放
     * mostPlay 最多播放
     * starId	否	String	明星ID
     classifyId	否	String	分类ID
     tagId	否	String	标签ID
     tagIds	否	String	标签ID集合，多个标签以逗号分隔
     pageNum	否	int	页数
     tagName	否	String	标签名称
     */
    public void fetchListByClassId(DetailListRequest request) {

        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("pageNum",String.valueOf(request.getPageNum()));
        if (request.getMostCare() != 0) {
            paramsMap.put("mostCare","1");
        }
        if (request.getNewVideo() != 0) {
            paramsMap.put("newVideo","1");
        }
        if (request.getMostPlay() != 0) {
            paramsMap.put("mostPlay","1");
        }
        if (request.getStarId() != null) {
            paramsMap.put("starId",request.getStarId());
        }
        if (request.getClassifyId() != null) {
            paramsMap.put("classifyId",request.getClassifyId());
        }
        if (request.getTagId() != null) {
            paramsMap.put("tagId",request.getTagId());
        }
        if (request.getTagIds() != null) {
            paramsMap.put("tagIds",request.getTagIds());
        }
        if (request.getTagName() != null) {
            paramsMap.put("tagName",request.getTagName());
        }

        requestDateNew(NetService.getInstance().getVideoByStarId(paramsMap), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                DetailListBean bean = (DetailListBean) obj;
                mDetailListView.refreshList(bean);
            }
            @Override
            public void onFaild(Object obj) {
                mDetailListView.showNetError();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mDetailListView.showNetError();
            }
        });
    }

}
