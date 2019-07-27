package com.baby.app.modules.mine.page;


import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.mine.MyPromoteBean;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.adapter.MyPromoteAdapter;
import com.baby.app.modules.mine.presenter.MyPromotePresenter;
import com.baby.app.modules.mine.view.IMyPromoteView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class MyPromoteActivity extends IBaseActivity implements IMyPromoteView {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.promote_top_1)
    LinearLayout promote_top_1;
    @BindView(R.id.promote_top_2)
    LinearLayout promote_top_2;

    @BindView(R.id.promote_recycle_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.bottom_view)
    RelativeLayout bottom_view;

    @BindView(R.id.totol_text)
    TextView totol_text;

    private MyPromoteAdapter mMyPromoteAdapter;
    private List<MyPromoteBean.Data>dataList = new ArrayList<>();
    private MyPromotePresenter mMyPromotePresenter;
    private View emptyView;

    private int posion = 0;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_my_promote;
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

        mTitleBuilder.seTitleBgColor(Color.parseColor("#FF000000"));
        mTitleBuilder.setMiddleTitleText("我的推广").setMiddleTitleTextColor(Color.parseColor("#FFFFFFFF"))
                .setLeftDrawable(R.mipmap.ic_white_brown);
        findViewById(com.android.baselibrary.R.id.title_line).setVisibility(View.GONE);

    }

    @Override
    public void initUiAndListener() {

        tab_layout.addTab(tab_layout.newTab().setText("我的推广"));
        tab_layout.addTab(tab_layout.newTab().setText("我的收益"));

        mMyPromotePresenter = new MyPromotePresenter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMyPromoteAdapter = new MyPromoteAdapter(R.layout.item_my_promote_layout,dataList);
        mRecyclerView.setAdapter(mMyPromoteAdapter);

        //失败页面
        emptyView = getLayoutInflater().inflate(R.layout.cache_error_layout, null);
//        emptyView.setVisibility(View.INVISIBLE);
        mMyPromoteAdapter.setEmptyView(emptyView);
        RelativeLayout error_root_view = (RelativeLayout) emptyView.findViewById(R.id.error_root_view);
        error_root_view.setBackgroundColor(Color.parseColor("#00ffffff"));
        TextView textView =  (TextView) emptyView.findViewById(R.id.empty_msg_text_view);
        textView.setText("您还没有推广好友");
        textView.setTextColor(Color.parseColor("#ffffff"));
        TextView subMsg =  (TextView) emptyView.findViewById(R.id.empty_sub_msg_text_view);
        subMsg.setText("请去推广页面推广好友增加观影次数吧！");
        subMsg.setTextColor(Color.parseColor("#6e6e6e"));
        emptyView.setVisibility(View.INVISIBLE);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mMyPromotePresenter.fetchData();

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab!=null) {
                    if (tab.getPosition() == 0) {
                        promote_top_1.setVisibility(View.VISIBLE);
                        promote_top_2.setVisibility(View.GONE);
                        mMyPromotePresenter.fetchData();
                        posion = 0;
                        bottom_view.setVisibility(View.GONE);
                    } else {
                        promote_top_2.setVisibility(View.VISIBLE);
                        promote_top_1.setVisibility(View.GONE);
                        mMyPromotePresenter.fetchMyincome();
                        posion = 1;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void refreshMyPromote(MyPromoteBean myPromoteBean) {
        dataList.clear();
        if (myPromoteBean.getData()!=null && myPromoteBean.getData().size() > 0) {
            emptyView.setVisibility(View.INVISIBLE);
            mMyPromoteAdapter.addData(myPromoteBean.getData());
            if (posion == 1) {
                bottom_view.setVisibility(View.VISIBLE);
                totol_text.setText("收益总计:"+myPromoteBean.getToIncome()+"元");
            }
        } else {
            emptyView.setVisibility(View.VISIBLE);
            if (posion == 0) {
                TextView textView =  (TextView) emptyView.findViewById(R.id.empty_msg_text_view);
                textView.setText("您还没有推广好友");
                textView.setTextColor(Color.parseColor("#ffffff"));
                TextView subMsg =  (TextView) emptyView.findViewById(R.id.empty_sub_msg_text_view);
                subMsg.setVisibility(View.VISIBLE);
                subMsg.setText("请去推广页面推广好友增加观影次数吧！");
                subMsg.setTextColor(Color.parseColor("#6e6e6e"));
            } else {
                bottom_view.setVisibility(View.VISIBLE);
                totol_text.setText("收益总计:"+"0元");
                TextView textView =  (TextView) emptyView.findViewById(R.id.empty_msg_text_view);
                textView.setText("您还没收益");
                textView.setTextColor(Color.parseColor("#ffffff"));
                TextView subMsg =  (TextView) emptyView.findViewById(R.id.empty_sub_msg_text_view);
                subMsg.setVisibility(View.INVISIBLE);
                subMsg.setText("请去推广页面推广好友增加观影次数吧！");
                subMsg.setTextColor(Color.parseColor("#6e6e6e"));
            }
        }
    }

    @Override
    public void showNetError() {
        super.showNetError();
        emptyView.setVisibility(View.VISIBLE);

    }
}
