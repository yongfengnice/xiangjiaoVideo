package com.baby.app.modules.find;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.find.FindBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.RefreshLayout;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.application.IBaseFragment;
import com.baby.app.modules.find.adapter.FindAdapter;
import com.baby.app.modules.find.presenter.FindPresenter;
import com.baby.app.modules.find.view.FindView;
import com.baby.app.modules.home.MainActivity;
import com.baby.app.modules.mine.DownEvent;
import com.baby.app.modules.search.page.SearchActivity;
import com.baby.app.service.DownInfoModel;

import org.simple.eventbus.Subscriber;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by yongqianggeng on 2018/9/19.
 * 发现
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class FindFragment extends IBaseFragment implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener,FindView {

    public static final String TAG = "FindFragment";

    private RecyclerView mRecyclerView;
    private RefreshLayout mSwipeRefreshLayout;

    private FindAdapter mFindAdapter;
    private List<FindBean.Data>findBeanList = new ArrayList<>();

    private FindPresenter mFindPresenter;

    private int pageNum = 1;

    private final LoadRunnable mLoadRunnable = new LoadRunnable(FindFragment.this);
    private FindBean mFindBean;

    private DownInfoModel model = new DownInfoModel();

    @Override
    protected int getLayoutView() {
         return R.layout.fragment_find;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (model.getNoCachBean()!=null && model.getNoCachBean().size() > 0) {
            mFindAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initUiAndListener(View view) {
        view.findViewById(com.android.baselibrary.R.id.title_line).setVisibility(View.GONE);
        mFindPresenter = new FindPresenter(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.find_recycler_view);
        mSwipeRefreshLayout = (RefreshLayout) view.findViewById(R.id.find_swipeLayout);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ScreenUtil.dip2px(mContext, 10);
            }
        });

        mFindAdapter = new FindAdapter(R.layout.item_find_layout, findBeanList);
        mRecyclerView.setAdapter(mFindAdapter);
        mFindAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FindBean.Data data = findBeanList.get(position);
                jumpToVideo(data.getId(),data.getVideoName(),data.getVideoUrl());
            }
        });

        mFindAdapter.setmFindAdapterLisenter(new FindAdapter.FindAdapterLisenter() {
            @Override
            public void onLike(FindBean.Data findBean) {
                if (UserStorage.getInstance().getUserType() == UserType.MARK_USER) {
                    mFindPresenter.setCareHistory(String.valueOf(findBean.getId()));
                } else {
                    jumpToLogin();
                }

            }

            @Override
            public void onDown(FindBean.Data findBean) {
               mFindPresenter.getCacheNum(findBean);
            }

            @Override
            public void onShare(FindBean.Data findBean) {
                if (mFindBean != null) {
                    @SuppressLint("WrongConstant") ClipboardManager cm = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(mFindBean.getExtensionInfo().getExtensionContext());
                    ToastUtil.showLongToast("请分享粘贴，已复制到系统剪切板");
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mFindAdapter.openLoadAnimation();
        mFindAdapter.setOnLoadMoreListener(this);
        mFindPresenter.fetchData(1);

    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.seTitleBgColor(Color.parseColor("#FFFFFF"));
        mTitleBuilder.setMiddleTitleText("发现").setMiddleTitleTextColor(Color.parseColor("#FF000000")).setRightImageRes(R.mipmap.search_back);
    }

    @Override
    public void onTitleButtonClicked(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                break;
            case MIDDLE:
                break;
            case RIGHT:
                openActivity(SearchActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        mFindPresenter.fetchData(pageNum);
    }

    //加载更多
    static class LoadRunnable implements Runnable {
        WeakReference<FindFragment > mFregmentReference;
        public LoadRunnable(FindFragment homeMarkFragment){
            mFregmentReference = new WeakReference<FindFragment>(homeMarkFragment);
        }

        @Override
        public void run() {
            if (mFregmentReference != null) {
                final FindFragment fregment = mFregmentReference.get();
                fregment.mFindPresenter.fetchData(fregment.pageNum);
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {

        new Handler().postDelayed(mLoadRunnable, 100);
    }

    @Subscriber(tag = DownEvent.TAG)
    public void jiexi(DownEvent event) {
    if (currentBean !=null) {
        mFindPresenter.usedViewOrCacheNum("2",String.valueOf(currentBean.getId()));
    }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFindAdapter.notifyDataSetChanged();
            }
        },300);
    }


    @Override
    public void refreshList(FindBean findBean) {
        mFindBean = findBean;
        if (pageNum == 1) {
            findBeanList.clear();
        }

        if (findBean.getData().size() > 0) {
            pageNum++;
            mFindAdapter.addData(findBean.getData());
        }
        if (findBeanList.size() == 0 || findBean.getPages() <= pageNum) {
            mFindAdapter.loadMoreEnd();
        } else {
            //加载更多完成
            mFindAdapter.loadMoreComplete();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void reStartNamalLogin() {
        super.reStartNamalLogin();
        onRefresh();
    }

    @Override
    public void showNetError() {
        super.showNetError();
        mSwipeRefreshLayout.setRefreshing(false);
        mFindAdapter.loadMoreFail();
    }

    private FindBean.Data currentBean;
    @Override
    public void isCanDown(FindBean.Data findBean) {
        currentBean = findBean;
        ((MainActivity)getActivity()).startDownLoad(findBean.getVideoUrl()
                ,findBean.getVideoName(),findBean.getVideoCover(),String.valueOf(findBean.getId()));
    }
}
