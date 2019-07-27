package com.baby.app.modules.mine.page;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.page.CommonWebViewActivity;
import com.android.baselibrary.service.UrlConstants;
import com.android.baselibrary.service.bean.mine.PayBean;
import com.android.baselibrary.service.bean.mine.PayRecharegeBean;
import com.android.baselibrary.service.bean.mine.VipBean;
import com.android.baselibrary.service.bean.mine.WithDrawBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.adapter.VipAdapter;
import com.baby.app.modules.mine.presenter.VipPresenter;
import com.baby.app.modules.mine.view.IVipView;
import com.baby.app.widget.MyDialogUtil;
import com.baby.app.widget.dialog.DimandDialog;
import com.baby.app.widget.dialog.PayDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yongqianggeng on 2018/10/5.
 * vip会员
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class VipActivity extends IBaseActivity implements IVipView{


    @BindView(R.id.vip_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.vip_text_view)
    TextView vipTextView;
    @BindView(R.id.vip_top_view)
    RelativeLayout vip_top_view;

    @BindView(R.id.vip_top_view2)
    RelativeLayout vip_top_view2;

    @BindView(R.id.vip_top_text1)
    TextView vip_top_text1;
    @BindView(R.id.vip_top_text2)
    TextView vip_top_text2;

    @BindView(R.id.vip_btn)
    RelativeLayout vip_btn;

    @BindView(R.id.vip_btn2)
    RelativeLayout vip_btn2;

    @BindView(R.id.dimand_btn)
    RelativeLayout dimand_btn;

    private VipAdapter mVipAdapter;

    private VipPresenter mVipPresenter;

    private List<VipBean.Data>vipBeanList = new ArrayList<>();

    private VipBean.Data currentData;
    private int payType = 1;
    private String tradeNo;

    private int selectType = 2;  //是否使用钻石抵扣 1=使用 2=不使用

    private WithDrawBean withDrawBean;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_vip;
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
                Bundle bundle = new Bundle();
                bundle.putString("url", UrlConstants.FEED_BAKK_URL);
                openActivity(CommonWebViewActivity.class,bundle);
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("VIP会员")
                .setLeftDrawable(R.mipmap.ic_back_brown).setRightText("联系客服")
                .setRightTextColor(Color.parseColor("#FF000000"));
    }

    @Override
    public void initUiAndListener() {

        dimand_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", UrlConstants.DIAMOND_URL);
                openActivity(CommonWebViewActivity.class,bundle);
            }
        });

        mVipPresenter = new VipPresenter(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                outRect.bottom = ScreenUtil.dip2px(mContext, 12);
            }
        });
        mVipAdapter = new VipAdapter(R.layout.item_vip_layout, vipBeanList);
        mRecyclerView.setAdapter(mVipAdapter);
        mVipAdapter.setmVipAdapterLisnter(new VipAdapter.VipAdapterLisnter() {
            @Override
            public void gotoBuy(VipBean.Data data) {
                currentData = data;
                //s
                if (withDrawBean.getCurrentCronNum() > 10) {
                    String msg = "您当前钻石余额为"+withDrawBean.getCurrentCronNum()+"，是否使用"+withDrawBean.getMostCron()+"钻石抵扣"+withDrawBean.getMostPrice()+"元";
                    MyDialogUtil.showDimandDialog(VipActivity.this,msg, new DimandDialog.DimandDialogLisenter() {
                        @Override
                        public void onCommitClick() {
                            selectType = 1;
                            mVipPresenter.fetchPayTypeList();
                        }

                        @Override
                        public void onNoCommitClick() {
                            selectType = 2;
                            mVipPresenter.fetchPayTypeList();
                        }
                    });
                } else {
                    selectType = 2;
                    mVipPresenter.fetchPayTypeList();
                }

            }
        });



        vip_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(RechargeActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tradeNo != null && tradeNo.length() > 0) {
            mVipPresenter.getPayStatus(tradeNo);
            tradeNo = "";
        }
        mVipPresenter.fetchData();
        mVipPresenter.fetchVipList();
    }

    @Override
    public void refreshVip(VipBean vipBean) {
        hideDialogLoading();
        vipBeanList.clear();
        mVipAdapter.addData(vipBean.getData());
        vipTextView.setText(vipBean.getMemberVipInfo());
        if (UserStorage.getInstance().getIsVip() == 0) {
            vip_top_view.setVisibility(View.GONE);
            vip_top_view2.setVisibility(View.VISIBLE);
        } else {
            vip_top_view.setVisibility(View.VISIBLE);
            vip_top_view2.setVisibility(View.GONE);
        }
    }

    @Override
    public void refreshPayType(PayBean payBean) {
        MyDialogUtil.showPayDialogDialog(VipActivity.this,payBean.getData(), new PayDialog.PayDialogDialogLisenter() {
            @Override
            public void onWxClick(final String payType) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDialogLoading();
                        if (currentData!=null) {
                            mVipPresenter.payRecharge(String.valueOf(currentData.getId()),payType,String.valueOf(selectType));
                        }
                    }
                },100);

            }

            @Override
            public void onZfbClick(final String payType) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDialogLoading();
                        if (currentData!=null) {
                            mVipPresenter.payRecharge(String.valueOf(currentData.getId()),payType,String.valueOf(selectType));
                        }
                    }
                },100);
            }
        });
    }

    @Override
    public void refreshPayRecharge(PayRecharegeBean payRecharegeBean) {
        tradeNo = payRecharegeBean.getTradeNo();
//        if (payType == 1) {
//            Bundle bundle = new Bundle();
//            bundle.putString("form",payRecharegeBean.getPayUrl());
//            openActivity(AlipayActivity.class,bundle);
//        } else {
//            Intent intent = new Intent();
//            intent.setData(Uri.parse(payRecharegeBean.getPayUrl()));//Url 就是你要打开的网址
//            intent.setAction(Intent.ACTION_VIEW); this.startActivity(intent); //启动浏览器
//        }
        Uri uri = Uri.parse(payRecharegeBean.getPayUrl());//Url 就是你要打开的网址
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);//启动浏览器
    }

    @Override
    public void dataCallBack(WithDrawBean withDrawBean) {
        this.withDrawBean = withDrawBean;
        vip_top_text1.setText(Html.fromHtml("<font size=\"16\" color=\"#ffffff\">当前有</font>" +
                "<font size=\"16\" color=\"#DBB185\">"+withDrawBean.getCurrentCronNum()+"</font>"+
                "<font size=\"16\" color=\"#ffffff\">钻石</font>"));

        vip_top_text2.setText(Html.fromHtml("<font size=\"14\" color=\"#ffffff\">购买vip最多可消耗</font>" +
                "<font size=\"14\" color=\"#DBB185\">"+withDrawBean.getMostCron()+"</font>"+
                "<font size=\"14\" color=\"#ffffff\">钻石，</font>"+
                "<font size=\"14\" color=\"#ffffff\">抵扣</font>"+
                "<font size=\"14\" color=\"#DBB185\">"+withDrawBean.getMostPrice()+"</font>"+"<font size=\"14\" color=\"#ffffff\">元</font>"));
    }
}
