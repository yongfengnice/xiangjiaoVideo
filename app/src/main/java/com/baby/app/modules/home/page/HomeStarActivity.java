package com.baby.app.modules.home.page;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.home.HomeClassBean;
import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.service.bean.home.StarDataBean;
import com.android.baselibrary.service.request.StarListRequest;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.RefreshLayout;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.home.adapter.ClassTopAdapter;
import com.baby.app.modules.home.adapter.StarListAdapter;
import com.baby.app.modules.home.persenter.StarListPresenter;
import com.baby.app.modules.home.view.StarListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yongqianggeng on 2018/10/1.
 * 人气明星
 */

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class HomeStarActivity extends IBaseActivity implements StarListView, SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.star_top_recycler_view)
    RecyclerView topRecycleView;
    @BindView(R.id.star_class_recycler_view)
    RecyclerView moreClassRecycleView;

    //列表
    @BindView(R.id.star_swipeLayout)
    RefreshLayout swipeLayout;
    @BindView(R.id.star_list_recycler_view)
    RecyclerView listRecycleView;

    private LoadRunnable mLoadRunnable  = new LoadRunnable(HomeStarActivity.this);

    private ClassTopAdapter mStarTopClassAdapter;
    private ClassTopAdapter mStarMoreClassAdapter;
    private StarListAdapter mStarListAdapter;

    //顶部分类
    private List<HomeClassBean> topClassBeanList = new ArrayList<>();
    //更多分类
    private List<HomeClassBean> moreClassBeanList = new ArrayList<>();
    //明星列表
    private List<HomeStarBean> homeStarBeanList = new ArrayList<>();

    private StarListPresenter mStarListPresenter;
    private StarListRequest mStarListRequest = new StarListRequest();

    @Override
    protected int getLayoutView() {
        return R.layout.activity_home_star;
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
        mTitleBuilder.setMiddleTitleText("人气明星")
                .setLeftDrawable(R.mipmap.ic_back_brown);
    }

    @Override
    public void initUiAndListener() {
        mStarListPresenter = new StarListPresenter(this);
        mStarListRequest.setCup("-1");
        mStarListRequest.setCupName("全部");
        mStarListRequest.setPageNum(1);
        mStarListRequest.setNewVideo("1");
        mStarListRequest.setVideoNum("2");
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setRefreshing(true);
        initTopRecycleView();
        initMoreClassRecycleView();
        initListRecycleView();
        mStarListAdapter.openLoadAnimation();
        mStarListAdapter.setOnLoadMoreListener(this);

        mStarListPresenter.fetchData(mStarListRequest);
    }

    private void initTopRecycleView(){

        HomeClassBean classBean1 = new HomeClassBean();
        classBean1.setName("人气最高");
        classBean1.setValue("1");
        topClassBeanList.add(classBean1);

        HomeClassBean classBean2 = new HomeClassBean();
        classBean2.setValue("2");
        classBean2.setName("片量最多");
        topClassBeanList.add(classBean2);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        topRecycleView.setLayoutManager(manager);
        topRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                if (pos ==0) {
                    outRect.left = ScreenUtil.dip2px(mContext, 15);
                    outRect.right = ScreenUtil.dip2px(mContext, 24);
                } else {
                    outRect.left = ScreenUtil.dip2px(mContext, 0);
                    outRect.right = ScreenUtil.dip2px(mContext, 24);
                }
            }
        });
        mStarTopClassAdapter = new ClassTopAdapter(R.layout.item_class_top_layout, topClassBeanList);
        mStarTopClassAdapter.getStringSets().add("人气最高");
        topRecycleView.setAdapter(mStarTopClassAdapter);
        mStarTopClassAdapter.setClassTopAdapterStarLisenter(new ClassTopAdapter.ClassTopAdapterStarLisenter() {
            @Override
            public void setClass(String name, String value) {
                mStarListRequest.setPageNum(1);
                if (value.equals("1")) { //人气最高
                    mStarListRequest.setNewVideo("1");
                    mStarListRequest.setVideoNum("2");
                } else { //片量最多
                    mStarListRequest.setNewVideo("2");
                    mStarListRequest.setVideoNum("1");
                }
                mStarListPresenter.fetchData(mStarListRequest);
                swipeLayout.setRefreshing(true);
            }
        });
    }

    private void initMoreClassRecycleView(){

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        moreClassRecycleView.setLayoutManager(manager);
        moreClassRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                if (pos ==0) {
                    outRect.left = ScreenUtil.dip2px(mContext, 15);
                    outRect.right = ScreenUtil.dip2px(mContext, 24);
                } else {
                    outRect.left = ScreenUtil.dip2px(mContext, 0);
                    outRect.right = ScreenUtil.dip2px(mContext, 24);
                }
            }
        });
        mStarMoreClassAdapter = new ClassTopAdapter(R.layout.item_class_top_layout, moreClassBeanList);
        mStarMoreClassAdapter.getStringSets().add("全部");
        moreClassRecycleView.setAdapter(mStarMoreClassAdapter);
        mStarMoreClassAdapter.setClassTopAdapterStarLisenter(new ClassTopAdapter.ClassTopAdapterStarLisenter() {
            @Override
            public void setClass(String name, String value) {
                mStarListRequest.setPageNum(1);
                mStarListRequest.setCupName(name);
                mStarListRequest.setCup(value);
                mStarListPresenter.fetchData(mStarListRequest);
                swipeLayout.setRefreshing(true);
            }
        });
    }

    private void initListRecycleView(){

        listRecycleView.setLayoutManager(new GridLayoutManager(mContext, 3));
        listRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                if (pos%3 ==0) {
                    outRect.left = ScreenUtil.dip2px(mContext, 6);
                    outRect.right = ScreenUtil.dip2px(mContext, 1);
                } else if (pos%3 == 1) {
                    outRect.left = ScreenUtil.dip2px(mContext, 0);
                    outRect.right = ScreenUtil.dip2px(mContext, 1);
                } else {
                    outRect.left = ScreenUtil.dip2px(mContext, 0);
                    outRect.right = ScreenUtil.dip2px(mContext, 6);
                }
                outRect.bottom = ScreenUtil.dip2px(mContext, 15);
            }
        });
        mStarListAdapter = new StarListAdapter(R.layout.item_star_list_layout, homeStarBeanList);
        mStarListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(StarDetailActivity.STAR_DETAIL_TYPE,homeStarBeanList.get(position));
                openActivity(StarDetailActivity.class,bundle);
            }
        });
        listRecycleView.setAdapter(mStarListAdapter);
    }

    @Override
    public void onRefresh() {
        mStarListRequest.setPageNum(1);
        mStarListPresenter.fetchData(mStarListRequest);
    }

    //加载更多
    static class LoadRunnable implements Runnable {
        WeakReference<HomeStarActivity > mActivityReference;
        public LoadRunnable(HomeStarActivity starActivity){
            mActivityReference = new WeakReference<HomeStarActivity>(starActivity);
        }

        @Override
        public void run() {
            if (mActivityReference != null) {
                final HomeStarActivity activity = mActivityReference.get();
                activity.mStarListPresenter.fetchData(activity.mStarListRequest);
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {

        new Handler().postDelayed(mLoadRunnable, 100);
    }

    @Override
    public void refresh(StarDataBean starDataBean) {
        moreClassBeanList.clear();
        moreClassBeanList.addAll(starDataBean.getCupList());
        refreshSelectedClass();
        mStarMoreClassAdapter.notifyDataSetChanged();

        if (mStarListRequest.getPageNum() == 1) {
            homeStarBeanList.clear();
        }

        if (starDataBean.getData().size() > 0) {
            mStarListRequest.morePage();
            mStarListAdapter.addData(starDataBean.getData());
        }
        if (starDataBean.getData().size() == 0 || starDataBean.getPages() <= mStarListRequest.getPageNum()) {
            mStarListAdapter.loadMoreEnd();
        } else {
            //加载更多完成
            mStarListAdapter.loadMoreComplete();
        }
        swipeLayout.setRefreshing(false);
    }

    public void refreshSelectedClass(){

        for (int i=0;i<moreClassBeanList.size();i++) {
            HomeClassBean tempBean = moreClassBeanList.get(i);
            if (mStarListRequest.getCupName()!=null && tempBean.getValue().equals(mStarListRequest.getCupName())) {
                mStarMoreClassAdapter.getStringSets().clear();
                mStarMoreClassAdapter.getStringSets().add(tempBean.getName());
                break;
            }
        }
    }

    @Override
    public void showNetError() {
        super.showNetError();
        swipeLayout.setRefreshing(false);
    }
}
