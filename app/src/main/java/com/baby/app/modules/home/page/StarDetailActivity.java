package com.baby.app.modules.home.page;


import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.service.request.DetailListRequest;
import com.android.baselibrary.widget.NoScrollRecyclerView;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.google.gson.Gson;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.home.adapter.StarDetailAdapter;
import com.baby.app.modules.home.adapter.StarListAdapter;
import com.baby.app.modules.home.bean.StartDetailTypeBean;
import com.baby.app.modules.home.persenter.DetaiiListPresenter;
import com.baby.app.modules.home.view.DetailListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class StarDetailActivity extends IBaseActivity implements DetailListView {

    public static final String STAR_DETAIL_TYPE = "STAR_DETAIL_TYPE";

    @BindView(R.id.star_detail_list)
    NoScrollRecyclerView mlistView;


    private List<DetailListBean.Data>dataList = new ArrayList<>();
    private HomeStarBean homeStarBean;
    private StarDetailAdapter mStarDetailAdapter;
    private List<StartDetailTypeBean>typeBeanList = new ArrayList<>();
    private DetaiiListPresenter mDetaiiListPresenter;
    private DetailListRequest request = new DetailListRequest();

    private boolean first1 = true;
    private boolean first2 = true;
    private boolean first3 = true;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_star_detail;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                finish();
                break;
            case MIDDLE:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {

        setToolBarVisible(View.GONE);


    }

    @Override
    public void initUiAndListener() {

        homeStarBean = (HomeStarBean) (getIntent().getExtras().getSerializable(STAR_DETAIL_TYPE));
        request.setStarId(String.valueOf(homeStarBean.getId()));
        mDetaiiListPresenter = new DetaiiListPresenter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        mlistView.setHasFixedSize(true);
        mlistView.setNestedScrollingEnabled(false);
        mlistView.setLayoutManager(linearLayoutManager);
        typeBeanList.add(new StartDetailTypeBean(StartDetailTypeBean.LAYOUT_STAR_DEAIL_1));
        typeBeanList.add(new StartDetailTypeBean(StartDetailTypeBean.LAYOUT_STAR_DEAIL_2));
        typeBeanList.add(new StartDetailTypeBean(StartDetailTypeBean.LAYOUT_STAR_DEAIL_3));
        mStarDetailAdapter = new StarDetailAdapter(mContext,dataList,homeStarBean, typeBeanList,first1,first2,first3);
        mlistView.setAdapter(mStarDetailAdapter);
        mStarDetailAdapter.setmStarDetailAdapterLisenter(new StarDetailAdapter.StarDetailAdapterLisenter() {
            @Override
            public void setNewVideo() {
                request.setNewVideo(1);
                request.setMostPlay(0);
                mDetaiiListPresenter.fetchListByClassId(request);
            }

            @Override
            public void setMoreVideo() {
                request.setNewVideo(0);
                request.setMostPlay(1);
                mDetaiiListPresenter.fetchListByClassId(request);
            }

            @Override
            public void onItemClick(DetailListBean.Data starBean) {
                jumpToVideo(starBean.getId(),starBean.getVideoName(),starBean.getVideoUrl());
            }

            @Override
            public void back() {
                finish();
            }
        });


        mDetaiiListPresenter.fetchListByClassId(request);
    }


    @Override
    public void refreshList(DetailListBean listBean) {
        dataList.clear();
        mStarDetailAdapter.refresh(listBean.getData());

        first1 =false;
        first2 =false;
    }
}
