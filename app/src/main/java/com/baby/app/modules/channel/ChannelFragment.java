package com.baby.app.modules.channel;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.baselibrary.base.BaseFragment;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.channel.ChannelDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.channel.ChannelTagDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTypeBean;
import com.android.baselibrary.service.bean.channel.TagClassBean;
import com.android.baselibrary.service.bean.home.HomeBannerBean;
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
import com.baby.app.modules.channel.adapter.ChannelAdapter;
import com.baby.app.modules.channel.page.ChannelTagActivity;
import com.baby.app.modules.channel.page.TagListActivity;
import com.baby.app.modules.channel.presenter.ChannelPresenter;
import com.baby.app.modules.channel.view.ChannelView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/19.
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class ChannelFragment extends IBaseFragment implements  SwipeRefreshLayout.OnRefreshListener,ChannelView {

    public static final String TAG = "ChannelFragment";
    private ConvenientBanner convenientBanner;
    private RefreshLayout swipeLayout;

    private NoScrollRecyclerView rv_home_list;
    private RelativeLayout rl_home_list;

    private CardPageTransformer mTransformer;

    private ChannelAdapter multipleItemAdapter;

    private LinearLayout rightBtn;

    private List<ChannelTypeBean>listItems = new ArrayList<>();
    // banner图片数据
    private List<String> data_banner_string = new ArrayList<>();

    private ChannelDataBean mChannelDataBean;

    private ChannelPresenter mChannelPresenter;
    @Override
    protected int getLayoutView() {
        return R.layout.fragment_channel;
    }

    @Override
    public void initUiAndListener(View view) {

        mChannelPresenter = new ChannelPresenter(this);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        swipeLayout = (RefreshLayout) view.findViewById(R.id.c_swipeLayout);

        rv_home_list = (NoScrollRecyclerView) view.findViewById(R.id.c_rv_home_list);
        rl_home_list = (RelativeLayout) view.findViewById(R.id.c_rl_home_list);

        rightBtn = (LinearLayout) view.findViewById(R.id.c_right_btn);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ChannelTagActivity.class);
            }
        });

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setRefreshing(true);

        initAdapter();
        initBanner();
        mChannelPresenter.fetchData();

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
        multipleItemAdapter = new ChannelAdapter(listItems,mChannelDataBean, mContext);
        rv_home_list.setAdapter(multipleItemAdapter);
    }

    /**
     * 初始化banner
     */
    private void initBanner() {

        for (int position = 0; position < 2; position++){
            data_banner_string.add("11");
        }

        mTransformer = new CardPageTransformer(0.85f, 0.145f);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderViewChannel>() {
            @Override
            public NetworkImageHolderViewChannel createHolder() {
                return new NetworkImageHolderViewChannel();
            }
        }, data_banner_string);

        convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        convenientBanner.setPageIndicator(new int[]{R.mipmap.icon_home_banner_a, R.mipmap.icon_home_banner_aa});
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showBanner();
            }
        },10);
    }
    public void showBanner() {


        // 此处不能直接notifyDataSetChanged这样的话会有一些缓存的问题，需要重新为adapter设置资源
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderViewChannel>() {
            @Override
            public NetworkImageHolderViewChannel createHolder() {
                return new  NetworkImageHolderViewChannel();
            }
        }, data_banner_string);

        //
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mChannelPresenter.fetchData();
            }
        },100);
    }

    @Override
    public void refresh(ChannelDataBean channelDataBean) {
        swipeLayout.setRefreshing(false);
        mChannelDataBean = channelDataBean;
        //TODO:先处理banner
        data_banner_string.clear();
        listItems.clear();
        for (HomeBannerBean bannerBean:channelDataBean.getData().getBannerList()) {
            data_banner_string.add(bannerBean.getPicUrl());
        }
        showBanner();
        //TODO:处理列表
        if (channelDataBean.getData() != null && channelDataBean.getData().getHotTagList()!=null &&channelDataBean.getData().getHotTagList().size()>0) {
            listItems.add(new ChannelTypeBean(ChannelTypeBean.LAYOUT_TAG));
        }
        if (channelDataBean.getData() != null && channelDataBean.getData().getCareTagList()!=null &&channelDataBean.getData().getCareTagList().size()>0) {
            listItems.add(new ChannelTypeBean(ChannelTypeBean.LAYOUT_LIKE));
        }
        multipleItemAdapter = new ChannelAdapter(listItems, channelDataBean, mContext);
        rv_home_list.setAdapter(multipleItemAdapter);
        multipleItemAdapter.setChannelAdapterLisenter(new ChannelAdapter.ChannelAdapterLisenter() {
            @Override
            public void onItemClick(ChannelTagBean channelTagBean) {
                Bundle bundle = new Bundle();
                List<ChannelTagBean>list = new ArrayList<>();
                list.add(channelTagBean);
                bundle.putSerializable(TagListActivity.TAG_LIST_KEY, (Serializable) list);
                openActivity(TagListActivity.class,bundle);
            }
        });

    }

    @Override
    public void refreshTagClass(TagClassBean tagClassBean) {

    }

    @Override
    public void refreshChannelTag(ChannelTagDataBean channelTagDataBean) {

    }

    @Override
    public void showNetError() {
        super.showNetError();
        swipeLayout.setRefreshing(false);
    }

    // banner加载图片适配
    class NetworkImageHolderViewChannel implements Holder<String> {
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


}
