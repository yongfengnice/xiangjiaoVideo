package com.baby.app.modules.channel.page;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.channel.ChannelDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.channel.ChannelTagDataBean;
import com.android.baselibrary.service.bean.channel.TagClassBean;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.channel.adapter.TagBottomAdapter;
import com.baby.app.modules.channel.adapter.TagLeftAdapter;
import com.baby.app.modules.channel.adapter.TagRightAdapter;
import com.baby.app.modules.channel.presenter.ChannelPresenter;
import com.baby.app.modules.channel.view.ChannelView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class ChannelTagActivity extends IBaseActivity implements ChannelView {

    @BindView(R.id.c_t_left_recycle_view)
    RecyclerView leftRecyclerView;
    @BindView(R.id.c_t_right_recycle_view)
    RecyclerView rightRecyclerView;
    @BindView(R.id.c_t_bottom_recycle_view)
    RecyclerView bottomRecyclerView;

    private TagLeftAdapter mTagLeftAdapter;
    private TagRightAdapter mTagRightAdapter;
    private TagBottomAdapter mTagBottomAdapter;

    private ChannelPresenter mChannelPresenter;
    //分类
    private List<TagClassBean.Data>tagClassBeanList = new ArrayList<>();
    //标签
    private List<ChannelTagBean>tagSubClassBeanList = new ArrayList<>();
    private List<ChannelTagBean>tagBottomClassBeanList = new ArrayList<>();

    @Override
    protected int getLayoutView() {
        return R.layout.activity_channel_tag;
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
                if (tagBottomClassBeanList.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(TagListActivity.TAG_LIST_KEY, (Serializable) tagBottomClassBeanList);
                    openActivity(TagListActivity.class,bundle);
                } else {
                    ToastUtil.showToast("请选择标签");
                }

                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("标签筛选")
                .setLeftDrawable(R.mipmap.ic_back_brown).setRightText("确定").setRightTextColor(Color.parseColor("#FF000000"));
    }

    @Override
    public void initUiAndListener() {
        mChannelPresenter = new ChannelPresenter(this);
        initLeftRecycleView();
        initRightRecycleView();
        initBottomRecycleView();

        mChannelPresenter.fetchTagClassData();
    }

    private void initLeftRecycleView() {
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        leftRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ScreenUtil.dip2px(mContext, 0);
            }
        });
        mTagLeftAdapter = new TagLeftAdapter(R.layout.item_tag_left_layout, tagClassBeanList);
        mTagLeftAdapter.getStringSets().add("全部");
        leftRecyclerView.setAdapter(mTagLeftAdapter);
        mTagLeftAdapter.setmTagLeftAdapterLisenter(new TagLeftAdapter.TagLeftAdapterLisenter() {
            @Override
            public void onClick(TagClassBean.Data data) {
                mChannelPresenter.selectTagsByType(data.getId());
            }
        });
    }

    private void initRightRecycleView() {
        rightRecyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        rightRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ScreenUtil.dip2px(mContext, 18);
            }
        });
        mTagRightAdapter = new TagRightAdapter(R.layout.item_tag_right_layout, tagSubClassBeanList);
        mTagRightAdapter.setTagRightAdapterLisenter(new TagRightAdapter.TagRightAdapterLisenter() {
            @Override
            public void selectedSubClass(ChannelTagBean tagSubClassBean) {
                tagBottomClassBeanList.add(tagSubClassBean);
                mTagBottomAdapter.notifyDataSetChanged();
            }

            @Override
            public void unSelectedSubClass(ChannelTagBean tagSubClassBean) {
                tagBottomClassBeanList.remove(tagSubClassBean);
                mTagBottomAdapter.notifyDataSetChanged();
            }
        });
        rightRecyclerView.setAdapter(mTagRightAdapter);
    }

    private void initBottomRecycleView() {

        bottomRecyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        bottomRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ScreenUtil.dip2px(mContext, 10);
            }
        });
        mTagBottomAdapter = new TagBottomAdapter(R.layout.item_tag_bottom_layout, tagBottomClassBeanList);
        mTagBottomAdapter.setTagBottomAdapterLisenter(new TagBottomAdapter.TagBottomAdapterLisenter() {
            @Override
            public void deleteSubClass(ChannelTagBean tagSubClassBean) {
                tagBottomClassBeanList.remove(tagSubClassBean);
                mTagBottomAdapter.notifyDataSetChanged();

                mTagRightAdapter.getStringSets().remove(tagSubClassBean.getId()+"");
                mTagRightAdapter.notifyDataSetChanged();
            }
        });
        bottomRecyclerView.setAdapter(mTagBottomAdapter);
    }

    @Override
    public void refresh(ChannelDataBean channelDataBean) {

    }

    @Override
    public void refreshTagClass(TagClassBean tagClassBean) {
        mTagLeftAdapter.addData(tagClassBean.getData());
        mChannelPresenter.selectTagsByType(-1);
    }

    @Override
    public void refreshChannelTag(ChannelTagDataBean channelTagDataBean) {
        tagSubClassBeanList.clear();
        mTagRightAdapter.addData(channelTagDataBean.getData());
    }
}
