package com.baby.app.modules.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.baselibrary.base.BaseFragment;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.page.CommonWebViewActivity;
import com.android.baselibrary.service.UrlConstants;
import com.android.baselibrary.service.bean.mine.HistoryBean;
import com.android.baselibrary.service.bean.video.VideoInComeBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.widget.NoScrollRecyclerView;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseFragment;
import com.baby.app.modules.mine.adapter.MineTypeAdapter;
import com.baby.app.modules.mine.bean.MineTypeBean;
import com.baby.app.modules.mine.page.AcountActivity;
import com.baby.app.modules.mine.page.HistroyActivity;
import com.baby.app.modules.mine.page.MyCacheActivity;
import com.baby.app.modules.mine.page.MyLikeActivity;
import com.baby.app.modules.mine.page.NotificationActivity;
import com.baby.app.modules.mine.page.PromoteActivity;
import com.baby.app.modules.mine.page.SettingActivity;
import com.baby.app.modules.mine.page.VipActivity;
import com.baby.app.modules.mine.page.WithDrawActivity;
import com.baby.app.modules.mine.presenter.MinePresenter;
import com.baby.app.modules.mine.view.MineView;
import com.baby.app.modules.video.page.VideoActivity;
import com.baby.app.service.DownLoadInfo;
import com.baby.app.widget.MyDialogUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yongqianggeng on 2018/9/19.
 *
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class MineFragment extends IBaseFragment implements View.OnClickListener,MineView {

    public static final String TAG = "MineFragment";
    private NoScrollRecyclerView rv_main_list;
    private RelativeLayout rl_main_list;

    private RelativeLayout mineSettintBtn;

    private MineTypeAdapter mMineTypeAdapter;
    private List<MineTypeBean>mineTypeBeanList = new ArrayList<>();

    private MinePresenter mMinePresenter;

    private boolean isFirst = false;

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initUiAndListener(View view) {

        mineSettintBtn = (RelativeLayout) view.findViewById(R.id.mine_setting_btn);
        mineSettintBtn.setOnClickListener(this);

        rv_main_list = (NoScrollRecyclerView)view.findViewById(R.id.m_rv_home_list);
        rl_main_list = (RelativeLayout) view.findViewById(R.id.m_rl_home_list);
        initAdapter();
        mMinePresenter = new MinePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMineTypeAdapter.notifyDataSetChanged();
        fetchUserData();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            fetchUserData();
        }
    }

    public void fetchUserData() {
        mMinePresenter.fetchUserData();
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
        rv_main_list.setHasFixedSize(true);
        rv_main_list.setNestedScrollingEnabled(false);
        rv_main_list.setLayoutManager(linearLayoutManager);

        mineTypeBeanList.add(new MineTypeBean(MineTypeBean.LAYOUT_MINE_1));
        mineTypeBeanList.add(new MineTypeBean(MineTypeBean.LAYOUT_MINE_4));
        mineTypeBeanList.add(new MineTypeBean(MineTypeBean.LAYOUT_MINE_2));
        mineTypeBeanList.add(new MineTypeBean(MineTypeBean.LAYOUT_MINE_3));


        mMineTypeAdapter = new MineTypeAdapter(mineTypeBeanList, mContext);
        rv_main_list.setAdapter(mMineTypeAdapter);
        mMineTypeAdapter.setMineTypeAdapterLisenter(new MineTypeAdapter.MineTypeAdapterLisenter() {
            @Override
            //我的喜欢
            public void gotoMyLike() {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.KEY_INTENT_ACTIVITY,0);
                openActivity(HistroyActivity.class,bundle);
            }

            @Override
            //历史记录
            public void gotoHistory() {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.KEY_INTENT_ACTIVITY,1);
                openActivity(HistroyActivity.class,bundle);
            }

            @Override
            //我的缓存
            public void gotoMyCache() {
                openActivity(MyCacheActivity.class);
            }

            @Override
            //进入通知
            public void gotoNotification() {
                openActivity(NotificationActivity.class);
            }

            @Override
            //进入vip
            public void gotoVip() {
                openActivity(VipActivity.class);
            }

            @Override
            //提现
            public void gotoWithDraw() {
                openActivity(WithDrawActivity.class);
            }

            @Override
            //登录注册
            public void gotoLogin() {
               jumpToLogin();
            }

            @Override
            //火爆交流群
            public void gotoHotQun() {
                Bundle bundle = new Bundle();
                bundle.putString("url",UserStorage.getInstance().getQqurl());
                openActivity(CommonWebViewActivity.class,bundle);
            }
            @Override
            //推广
            public void gotoPromote() {
                openActivity(PromoteActivity.class);
            }

            @Override
            //账户管理
            public void gotoAcount() {
                if (UserStorage.getInstance().isLogin()&& UserStorage.getInstance().getUserType() == UserType.MARK_USER) {
                    openActivity(AcountActivity.class);
                } else {
                    jumpToLogin();
                }

            }

            @Override
            //意见反馈
            public void feedBack() {
                Bundle bundle = new Bundle();
                bundle.putString("url", UrlConstants.FEED_BAKK_URL);
                openActivity(CommonWebViewActivity.class,bundle);
            }

            @Override
            public void gotoDetail(HistoryBean historyBean) {
                jumpToVideo(historyBean.getVideoId(),historyBean.getVideoName(),null);
            }

            @Override
            public void gotoCacheDetail(DownLoadInfo downLoadInfo) {
                jumpToVideo2(Integer.parseInt(downLoadInfo.getVideoId()),downLoadInfo.getName(),downLoadInfo.getPath());
            }
        });
    }

    public void jumpToVideo2(int id, String name, String url) {
        VideoInComeBean videoInComeBean = new VideoInComeBean();
        videoInComeBean.setId(id);
        videoInComeBean.setIsCache(1);
        videoInComeBean.setVideoName(name);
        videoInComeBean.setVideoUrl(url);
        Bundle bundle = new Bundle();
        bundle.putSerializable(VideoActivity.VIDEO_KEY, (Serializable) videoInComeBean);
        openActivity(VideoActivity.class,bundle);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mine_setting_btn) {
            openActivity(SettingActivity.class);
        }
    }

    @Override
    public void refreshMineData(boolean isRefresh) {

        mMineTypeAdapter.notifyDataSetChanged();
        if (UserStorage.getInstance().getInfo()!=null && UserStorage.getInstance().getInfo().getData()!=null) {
            if (UserStorage.getInstance().getInfo().getData().getMemberInfo().getIsRemind() == 1) {
                switch (UserStorage.getInstance().getSortNo()) {
                    case 0:
                        MyDialogUtil.showLevelDialog(getActivity(),R.mipmap.my_level_1);
                        break;
                    case 1:
                        MyDialogUtil.showLevelDialog(getActivity(),R.mipmap.my_level_1);
                        break;
                    case 2:
                        MyDialogUtil.showLevelDialog(getActivity(),R.mipmap.my_level_2);
                        break;
                    case 3:
                        MyDialogUtil.showLevelDialog(getActivity(),R.mipmap.my_level_3);
                        break;
                    case 4:
                        MyDialogUtil.showLevelDialog(getActivity(),R.mipmap.my_level_4);
                        break;
                    case 5:
                        MyDialogUtil.showLevelDialog(getActivity(),R.mipmap.my_level_5);
                        break;
                }

            }
        }

    }

    @Override
    public void reStartNamalLogin() {
        super.reStartNamalLogin();
        fetchUserData();
    }
}
