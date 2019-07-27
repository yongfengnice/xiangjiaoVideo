package com.baby.app.modules.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.baselibrary.base.BaseFragment;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.page.CommonWebViewActivity;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.home.HomeBannerBean;
import com.android.baselibrary.service.bean.home.HomeClassBean;
import com.android.baselibrary.service.bean.home.HomeDataBean;
import com.android.baselibrary.service.bean.home.HomeListBean;
import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.service.bean.video.VideoInComeBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.util.GlideUtils;
import com.android.baselibrary.widget.NoScrollRecyclerView;
import com.android.baselibrary.widget.RefreshLayout;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.transformer.CardPageTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.baby.app.R;
import com.baby.app.application.IBaseFragment;
import com.baby.app.modules.channel.page.TagListActivity;
import com.baby.app.modules.home.adapter.HomeTypeAdapter;
import com.baby.app.modules.home.bean.HomeTypeBean;
import com.baby.app.modules.home.page.HomeClassActivity;
import com.baby.app.modules.home.page.HomeStarActivity;
import com.baby.app.modules.home.page.StarDetailActivity;
import com.baby.app.modules.home.persenter.HomePresenter;
import com.baby.app.modules.home.view.HomeView;
import com.baby.app.modules.mine.page.ErActivity;
import com.baby.app.modules.mine.page.HistroyActivity;
import com.baby.app.modules.mine.page.MyCacheActivity;
import com.baby.app.modules.mine.page.MyPromoteActivity;
import com.baby.app.modules.mine.page.PromoteActivity;
import com.baby.app.modules.mine.page.VipActivity;
import com.baby.app.modules.search.page.SearchActivity;
import com.baby.app.modules.video.page.VideoActivity;
import com.baby.app.widget.LocalImageHolderView;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/19.
 * 首页
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class HomeFragment extends IBaseFragment implements  SwipeRefreshLayout.OnRefreshListener,HomeView {

    public static final String TAG = "HomeFragment";

    private ConvenientBanner convenientBanner;
    private SwipeRefreshLayout swipeLayout;

    private RelativeLayout searchBar;
    private EditText home_edit_text;

    private NoScrollRecyclerView rv_home_list;
    private RelativeLayout rl_home_list;
    private HomeTypeAdapter multipleItemAdapter;
    // 布局的类型
    List<HomeTypeBean> listItems = new ArrayList<>();
    private CardPageTransformer mTransformer;

    // banner图片数据
    private List<String> data_banner_string = new ArrayList<>();

    private HomePresenter mHomePresenter;

    private HomeDataBean mHomeDataBean;
    //缓存
    private RelativeLayout home_down_view;
    //历史记录
    private RelativeLayout home_history_view_back;

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initUiAndListener(View view) {

        mHomePresenter = new HomePresenter(this);

        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);

        rv_home_list = (NoScrollRecyclerView) view.findViewById(R.id.rv_home_list);
        rl_home_list = (RelativeLayout) view.findViewById(R.id.rl_home_list);

        searchBar = (RelativeLayout) view.findViewById(R.id.home_search_back_view);
        home_edit_text = (EditText) view.findViewById(R.id.home_edit_text);

        home_down_view = (RelativeLayout) view.findViewById(R.id.home_down_view);
        home_history_view_back = (RelativeLayout) view.findViewById(R.id.home_history_view_back);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setRefreshing(true);

        initAdapter();
        initBanner();

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SearchActivity.class);
            }
        });
        home_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SearchActivity.class);
            }
        });

        home_down_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MyCacheActivity.class);
            }
        });
        home_history_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.KEY_INTENT_ACTIVITY,1);
                openActivity(HistroyActivity.class,bundle);
            }
        });

        mHomePresenter.fetchHomeData();
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        setToolBarVisible(View.GONE);
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rv_home_list.setHasFixedSize(true);
        rv_home_list.setNestedScrollingEnabled(false);
        rv_home_list.setLayoutManager(linearLayoutManager);

        multipleItemAdapter = new HomeTypeAdapter(listItems, mHomeDataBean, mContext);
        rv_home_list.setAdapter(multipleItemAdapter);
    }

    /**
     * 初始化banner
     */
    private void initBanner() {

        for (int position = 0; position <= 1; position++){
            data_banner_string.add("11");
        }

        mTransformer = new CardPageTransformer(0.85f, 0.145f);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, data_banner_string);

        convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mHomeDataBean!=null && mHomeDataBean.getData()!=null) {
                    if (mHomeDataBean.getData().getBannerList() != null) {
                        HomeBannerBean homeBannerBean = mHomeDataBean.getData().getBannerList().get(position);
                        if (homeBannerBean.getLinkType() == 1) {
                            mHomePresenter.clickAd(String.valueOf(homeBannerBean.getId()));
                        }
                        jumpToTagActivity(homeBannerBean);
                    }
                }
            }
        });
        convenientBanner.setPageIndicator(new int[]{R.mipmap.icon_home_banner_aa, R.mipmap.icon_home_banner_a});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        convenientBanner.startTurning(5000);
        convenientBanner.setManualPageable(true);
        convenientBanner.setPageTransformer(mTransformer);
        if (convenientBanner.getViewPager() != null) {
            convenientBanner.getViewPager().setClipToPadding(false);
            convenientBanner.getViewPager().setClipChildren(false);
            try {
                ((RelativeLayout) convenientBanner.getViewPager().getParent()).setClipChildren(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            convenientBanner.getViewPager().setOffscreenPageLimit(3);
        }
    }
    public void showBanner() {
        // 此处不能直接notifyDataSetChanged这样的话会有一些缓存的问题，需要重新为adapter设置资源
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, data_banner_string);

        //
    }

    @Override
    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mHomePresenter.fetchHomeData();
            }
        });

    }


    @Override
    public void refreshHomeData(final HomeDataBean dataBean) {

        swipeLayout.setRefreshing(false);
        if (listItems.size() > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshData(dataBean);
                }
            },200);
        } else {
            refreshData(dataBean);
        }
    }

    private void refreshData(HomeDataBean dataBean){
        mHomeDataBean = dataBean;
        //TODO:先处理banner
        data_banner_string.clear();
        listItems.clear();
        for (HomeBannerBean bannerBean:dataBean.getData().getBannerList()) {
            data_banner_string.add(bannerBean.getPicUrl());
        }
        showBanner();
        //TODO:处理列表
        if (mHomeDataBean.getData() != null && mHomeDataBean.getData().getClassifyList()!=null &&mHomeDataBean.getData().getClassifyList().size()>0) {
            listItems.add(new HomeTypeBean(HomeTypeBean.LAYOUT_CLASS));
        }
        if (mHomeDataBean.getData() != null && mHomeDataBean.getData().getNewVideoList()!=null &&mHomeDataBean.getData().getNewVideoList().size()>0) {
            listItems.add(new HomeTypeBean(HomeTypeBean.LAYOUT_NEW_LIST));
        }
        if (mHomeDataBean.getData() != null && mHomeDataBean.getData().getMostVideoList()!=null &&mHomeDataBean.getData().getMostVideoList().size()>0) {
            listItems.add(new HomeTypeBean(HomeTypeBean.LAYOUT_HOT_LIST));
        }
        if (mHomeDataBean.getData() != null && mHomeDataBean.getData().getStarList()!=null &&mHomeDataBean.getData().getStarList().size()>0) {
            listItems.add(new HomeTypeBean(HomeTypeBean.LAYOUT_MAN_LIST));
        }

        multipleItemAdapter = new HomeTypeAdapter(listItems, dataBean, mContext);
        rv_home_list.setAdapter(multipleItemAdapter);
        multipleItemAdapter.setmHomeTypeAdapterLisenter(new HomeTypeAdapter.HomeTypeAdapterLisenter() {
            @Override
            //分类点击
            public void onClassClick(HomeClassBean homeClassBean) {
                Bundle bundle = new Bundle();
                bundle.putInt(HomeClassActivity.HOME_CLASS_TYPE,1);
                bundle.putSerializable(HomeClassActivity.HOME_CLASS_KEY, (Serializable) homeClassBean);
                openActivity(HomeClassActivity.class,bundle);
            }
            //最新片源更多
            @Override
            public void onNewMoreMovies() {
                Bundle bundle = new Bundle();
                bundle.putInt(HomeClassActivity.HOME_CLASS_TYPE,2);
                bundle.putInt(HomeClassActivity.HOME_MORE_KEY,2);
                openActivity(HomeClassActivity.class,bundle);
            }

            @Override
            public void onStarMoreMovies() {
                openActivity(HomeStarActivity.class);
            }

            @Override
            public void onStarMovies(HomeStarBean homeStarBean) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(StarDetailActivity.STAR_DETAIL_TYPE,homeStarBean);
                openActivity(StarDetailActivity.class,bundle);
            }

            @Override
            //
            public void gotoVideo(HomeListBean listBean) {
                jumpToVideo(listBean.getId(),listBean.getVideoName(),listBean.getVideoUrl());
            }

            //最多片源更多
            @Override
            public void onHotMoreMovies() {
                Bundle bundle = new Bundle();
                bundle.putInt(HomeClassActivity.HOME_CLASS_TYPE,2);
                bundle.putInt(HomeClassActivity.HOME_MORE_KEY,1);
                openActivity(HomeClassActivity.class,bundle);
            }
        });
    }

    // banner加载图片适配
    class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_banner_item, null);
            imageView = (ImageView) view.findViewById(R.id.iv_banner_img);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            // 图片
            GlideUtils
                    .getInstance()
                    .LoadContextRoundAndCeterCropBitmapDefault(mContext,
                            data,
                            imageView,
                            R.mipmap.loading,
                            R.mipmap.loading,
                            GlideUtils.LOAD_BITMAP, 8);
        }
    }

    @Override
    public void showNetError() {
        super.showNetError();
        swipeLayout.setRefreshing(false);
    }
}
