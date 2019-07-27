package com.baby.app.advertise;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.page.CommonWebViewActivity;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.util.GlideUtils;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.transformer.CardPageTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.home.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class AdvertiseActivity extends IBaseActivity {

    @BindView(R.id.start_convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.start_time_text_view)
    TextView timeView;
    @BindView(R.id.start_back_view)
    RelativeLayout startBackView;

    private Runnable updateTimeRunnable;

    private Handler mHandler;

    int reg = 5;

    private boolean isJump = false;
    private List<String> data_banner_string = new ArrayList<>();
    @Override
    protected int getLayoutView() {
        return R.layout.activity_advertise;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {

    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        setToolBarVisible(View.GONE);
    }

    @Override
    public void initUiAndListener() {

        updateTimeRunnable = new UpdateTimeRunnable();
        final List<LoginBean.BannerBean>bannerBeanList = UserStorage.getInstance().getUser().getBannerList();
        if (UserStorage.getInstance().getUser().getBannerList()!=null) {
            for (int position = 0; position < bannerBeanList.size(); position++){
                LoginBean.BannerBean bannerBean = bannerBeanList.get(position);
                data_banner_string.add(bannerBean.getPicUrl());
            }
        }

        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, data_banner_string);

        convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LoginBean.BannerBean bannerBean = bannerBeanList.get(position);
                jumpToUserTagActivity(bannerBean);

            }
        });
        if (data_banner_string.size() >1) {
            convenientBanner.setCanLoop(true);
        } else {
            convenientBanner.setCanLoop(false);
        }
        convenientBanner.setPageIndicator(new int[]{R.mipmap.icon_home_banner_a, R.mipmap.icon_home_banner_aa});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        convenientBanner.startTurning(5000);
        convenientBanner.setManualPageable(true);
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

        mHandler = new Handler();
        mHandler.postDelayed(updateTimeRunnable,1000);
        startBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isJump) {
                    openActivity(MainActivity.class);
                    finish();
                }
            }
        });
    }

    // banner加载图片适配
    class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(AdvertiseActivity.this).inflate(R.layout.start_banner_item, null);
            imageView = (ImageView) view.findViewById(R.id.iv_banner_img);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            // 图片
            GlideUtils
                    .getInstance()
                    .LoadNewContextBitmap(mContext,
                            data,
                            imageView,
                            R.mipmap.loading,
                            R.mipmap.loading,
                            GlideUtils.LOAD_BITMAP);
        }
    }

    private class UpdateTimeRunnable implements Runnable {

        public UpdateTimeRunnable() {

        }

        @Override
        public void run() {
            if (reg == 0) {
                timeView.setText("跳过");
                isJump = true;
            } else {
                timeView.setText(reg+"");
                reg --;
                mHandler.postDelayed(this, 1000);
            }

        }
    }
}
