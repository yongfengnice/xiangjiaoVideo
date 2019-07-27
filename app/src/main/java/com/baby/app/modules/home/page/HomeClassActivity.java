package com.baby.app.modules.home.page;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.home.HomeClassBean;
import com.android.baselibrary.service.bean.home.HomeListBean;
import com.android.baselibrary.service.request.DetailListRequest;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.RefreshLayout;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.home.adapter.ClassListAdapter;
import com.baby.app.modules.home.adapter.ClassTopAdapter;
import com.baby.app.modules.home.persenter.DetaiiListPresenter;
import com.baby.app.modules.home.view.DetailListView;
import com.baby.app.modules.search.page.SearchActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * Created by yongqianggeng on 2018/9/19.
 * 分类/最新片源
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class HomeClassActivity extends IBaseActivity implements DetailListView,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    public static final String HOME_CLASS_TYPE = "HOME_CLASS_TYPE";
    public static final String HOME_CLASS_KEY = "HOME_CLASS_KEY";
    public static final String HOME_MORE_KEY = "HOME_MORE_KEY";

    @BindView(R.id.top_recycler_view)
    RecyclerView topRecycleView;
    @BindView(R.id.class_recycler_view)
    RecyclerView moreClassRecycleView;

    //列表
    @BindView(R.id.class_swipeLayout)
    RefreshLayout swipeLayout;
    @BindView(R.id.list_recycler_view)
    RecyclerView listRecycleView;

    private LoadRunnable mLoadRunnable  = new LoadRunnable(HomeClassActivity.this);

    private ClassTopAdapter mHomeTopClassAdapter;
    private ClassTopAdapter mHomeMoreClassAdapter;
    private ClassListAdapter classListAdapter;

    private LinearLayoutManager moreClassManager;
    private DetaiiListPresenter mDetaiiListPresenter;
    private DetailListRequest request = new DetailListRequest();
    private int beforeType; //1 分类进入 2 更多进入
    private int mostType; //1.最多播放 2 最近更新 3 最多喜欢
    private HomeClassBean beforeClassBean;

    //顶部分类
    private List<HomeClassBean>topClassBeanList = new ArrayList<>();
    //更多分类
    private List<HomeClassBean>moreClassBeanList = new ArrayList<>();
    //列表数据
    private List<DetailListBean.Data>dataArrayList = new ArrayList<>();
    @Override
    protected int getLayoutView() {
        return R.layout.activity_home_class;
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
                openActivity(SearchActivity.class);
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.seTitleBgColor(Color.parseColor("#FF000000"));

        mTitleBuilder.setMiddleTitleText("分类")
                .setLeftDrawable(R.mipmap.ic_white_brown).setRightImageRes(R.mipmap.search_white);
        mTitleBuilder.setMiddleTitleTextColor(Color.parseColor("#FFFFFFFF"));
    }

    @Override
    public void initUiAndListener() {
        mDetaiiListPresenter = new DetaiiListPresenter(this);

        beforeType =  getIntent().getExtras().getInt(HOME_CLASS_TYPE);
        if (beforeType == 1) {
            beforeClassBean = (HomeClassBean) getIntent().getExtras().getSerializable(HOME_CLASS_KEY);
            mostType = 1;
            request.setMostPlay(1);
            request.setClassifyId(String.valueOf(beforeClassBean.getId()));
        } else {
            mostType = getIntent().getExtras().getInt(HOME_MORE_KEY);
            //1.最多播放 2 最近更新 3 最多喜欢
            if (mostType==1) { //最多播放
                request.setMostPlay(1);
                request.setNewVideo(0);
                request.setMostCare(0);
            } else if (mostType ==2) { //最近更新
                request.setMostPlay(0);
                request.setNewVideo(1);
                request.setMostCare(0);
            } else { //最多喜欢
                request.setMostPlay(0);
                request.setNewVideo(0);
                request.setMostCare(1);
            }
        }
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setRefreshing(true);

        initTopRecycleView();
        initMoreClassRecycleView();
        initListRecycleView();
        request.setPageNum(1);
        mDetaiiListPresenter.fetchListByClassId(request);
    }

    private void initTopRecycleView(){

        HomeClassBean classBean1 = new HomeClassBean();
        classBean1.setId(1);
        classBean1.setName("最多播放");
        topClassBeanList.add(classBean1);

        HomeClassBean classBean2 = new HomeClassBean();
        classBean2.setName("最近更新");
        classBean2.setId(2);
        topClassBeanList.add(classBean2);

        HomeClassBean classBean3 = new HomeClassBean();
        classBean3.setName("最多喜欢");
        classBean3.setId(3);
        topClassBeanList.add(classBean3);


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
        mHomeTopClassAdapter = new ClassTopAdapter(R.layout.item_class_top_layout, topClassBeanList);

        //1.最多播放 2 最近更新 3 最多喜欢
        if (beforeType == 2) {
            if (mostType == 1) { //最多播放
                mHomeTopClassAdapter.getStringSets().add("最多播放");
            } else if (mostType == 2) { //最近更新
                mHomeTopClassAdapter.getStringSets().add("最近更新");
            } else { //最多喜欢
                mHomeTopClassAdapter.getStringSets().add("最多喜欢");
            }
        } else if (beforeType == 1) {
            mHomeTopClassAdapter.getStringSets().add("最多播放");
        }

        topRecycleView.setAdapter(mHomeTopClassAdapter);
        mHomeTopClassAdapter.setClassTopAdapterLisenter(new ClassTopAdapter.ClassTopAdapterLisenter() {
            @Override
            public void setClass(String classId) {
//                * mostCare 最多喜欢
//                * newVideo 最新播放
//                * mostPlay 最多播放
                request.setPageNum(1);
                if (classId.equals("1")) { //最多播放
                    request.setMostPlay(1);
                    request.setNewVideo(0);
                    request.setMostCare(0);
                } else if (classId.equals("2")) { //最近更新
                    request.setMostPlay(0);
                    request.setNewVideo(1);
                    request.setMostCare(0);
                } else { //最多喜欢
                    request.setMostPlay(0);
                    request.setNewVideo(0);
                    request.setMostCare(1);
                }
                mDetaiiListPresenter.fetchListByClassId(request);
                swipeLayout.setRefreshing(true);
            }
        });

    }

    private void initMoreClassRecycleView(){

        moreClassManager = new LinearLayoutManager(mContext);
        moreClassManager.setOrientation(OrientationHelper.HORIZONTAL);
        moreClassRecycleView.setLayoutManager(moreClassManager);
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
        mHomeMoreClassAdapter = new ClassTopAdapter(R.layout.item_class_top_layout, moreClassBeanList);
        if (beforeType == 2) {
            mHomeMoreClassAdapter.getStringSets().add("全部");
        }
        moreClassRecycleView.setAdapter(mHomeMoreClassAdapter);

        mHomeMoreClassAdapter.setClassTopAdapterLisenter(new ClassTopAdapter.ClassTopAdapterLisenter() {
            @Override
            public void setClass(String classId) {
                request.setPageNum(1);
                request.setClassifyId(classId);
                mDetaiiListPresenter.fetchListByClassId(request);
                swipeLayout.setRefreshing(true);
            }
        });
    }

    private void initListRecycleView(){

        listRecycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
        listRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                if (pos%2 ==0) {
                    outRect.left = ScreenUtil.dip2px(mContext, 10);
                    outRect.right = ScreenUtil.dip2px(mContext, 4);
                } else {
                    outRect.left = ScreenUtil.dip2px(mContext, 4);
                    outRect.right = ScreenUtil.dip2px(mContext, 10);
                }
                outRect.bottom = ScreenUtil.dip2px(mContext, 10);
            }
        });
        classListAdapter = new ClassListAdapter(R.layout.item_class_list_layout, dataArrayList);
        classListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dataArrayList.size() > 0) {
                    DetailListBean.Data detailListBean = dataArrayList.get(position);
                    jumpToVideo(detailListBean.getId(),detailListBean.getVideoName(),detailListBean.getVideoUrl());
                }

            }
        });
        listRecycleView.setAdapter(classListAdapter);
        classListAdapter.openLoadAnimation();
        classListAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void refreshList(DetailListBean listBean) {
        moreClassBeanList.clear();
        moreClassBeanList.addAll(listBean.getClassifyList());
        refreshSelectedClass();
        mHomeMoreClassAdapter.notifyDataSetChanged();

        if (request.getPageNum() == 1) {
            dataArrayList.clear();
        }

        if (listBean.getData().size() > 0) {
            request.morePage();
            classListAdapter.addData(listBean.getData());
        }
        if (listBean.getData().size() == 0 || listBean.getPages() <= request.getPageNum()) {
            classListAdapter.loadMoreEnd();
        } else {
            //加载更多完成
            classListAdapter.loadMoreComplete();
        }
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showNetError() {
        super.showNetError();
        swipeLayout.setRefreshing(false);
    }

    private boolean isLoadFirst = true;
    public void refreshSelectedClass(){

        for (int i=0;i<moreClassBeanList.size();i++) {
            HomeClassBean tempBean = moreClassBeanList.get(i);
            if (request.getClassifyId()!=null && tempBean.getId() == Integer.parseInt(request.getClassifyId())) {
                mHomeMoreClassAdapter.getStringSets().clear();
                mHomeMoreClassAdapter.getStringSets().add(tempBean.getName());
                if (beforeType == 1) {
                    if (isLoadFirst) {
                        isLoadFirst = false;
                        if (i >2) {
                            moreClassManager.scrollToPositionWithOffset(i, 20);
                        } else {
                            moreClassManager.scrollToPositionWithOffset(i, 0);
                        }
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        request.setPageNum(1);
        mDetaiiListPresenter.fetchListByClassId(request);
    }

    //加载更多
    static class LoadRunnable implements Runnable {
        WeakReference<HomeClassActivity > mActivityReference;
        public LoadRunnable(HomeClassActivity classActivity){
            mActivityReference = new WeakReference<HomeClassActivity>(classActivity);
        }

        @Override
        public void run() {
            if (mActivityReference != null) {
                final HomeClassActivity activity = mActivityReference.get();
                activity.mDetaiiListPresenter.fetchListByClassId(activity.request);
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {

        new Handler().postDelayed(mLoadRunnable, 100);
    }

}
