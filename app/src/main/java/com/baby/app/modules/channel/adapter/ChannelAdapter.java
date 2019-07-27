package com.baby.app.modules.channel.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseMultiItemQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.ChannelDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.channel.ChannelTypeBean;
import com.android.baselibrary.util.ScreenUtil;
import com.baby.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/28.
 */

public class ChannelAdapter extends BaseMultiItemQuickAdapter <ChannelTypeBean,BaseViewHolder>{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private Context mContext;
    private List<ChannelTypeBean> channelTypeBeans;
    private ChannelAdapterLisenter mChannelAdapterLisenter;

    private ChannelDataBean dataBean;

    public void setChannelAdapterLisenter(ChannelAdapterLisenter channelAdapterLisenter) {

        this.mChannelAdapterLisenter = channelAdapterLisenter;

    }

    public ChannelAdapter(List<ChannelTypeBean> dataList, ChannelDataBean dataBean, Context context) {
        super(dataList);
        this.mContext = context;
        this.channelTypeBeans = dataList;
        this.dataBean = dataBean;

        int size = channelTypeBeans.size();
        for (int i = 0; i < size; i++) {
            switch (channelTypeBeans.get(i).getItemType()) {
                case ChannelTypeBean.LAYOUT_TAG:
                    addItemType(ChannelTypeBean.LAYOUT_TAG, R.layout.channel_tag_layout);
                    break;
                case ChannelTypeBean.LAYOUT_LIKE:
                    addItemType(ChannelTypeBean.LAYOUT_LIKE, R.layout.channel_tag_layout);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, final ChannelTypeBean item) {
        switch (helper.getItemViewType()) {
            case ChannelTypeBean.LAYOUT_TAG: {
                ImageView c_tag_img_view = helper.getView(R.id.c_tag_img_view);
                c_tag_img_view.setImageResource(R.mipmap.channel_hot);
                TextView c_tag_text_view = helper.getView(R.id.c_tag_text_view);
                c_tag_text_view.setText("热门标签");
                final RecyclerView mRecyclerView = helper.getView(R.id.c_recycler_view);
                mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.bottom = ScreenUtil.dip2px(mContext, 20);
                    }
                });
                ChannelTagAdapter channelTagAdapter = new ChannelTagAdapter(R.layout.item_channel_tag_layout, dataBean.getData().getHotTagList());
                mRecyclerView.setAdapter(channelTagAdapter);
                channelTagAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mChannelAdapterLisenter != null) {
                            mChannelAdapterLisenter.onItemClick(dataBean.getData().getHotTagList().get(position));
                        }
                    }
                });

            }
            break;
            case ChannelTypeBean.LAYOUT_LIKE: {

                ImageView c_tag_img_view = helper.getView(R.id.c_tag_img_view);
                c_tag_img_view.setImageResource(R.mipmap.channel_like);
                TextView c_tag_text_view = helper.getView(R.id.c_tag_text_view);
                c_tag_text_view.setText("猜你喜欢");
                final RecyclerView mRecyclerView = helper.getView(R.id.c_recycler_view);
                mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.bottom = ScreenUtil.dip2px(mContext, 20);
                    }
                });
                ChannelLikeAdapter channelLikeAdapter = new ChannelLikeAdapter(R.layout.item_channel_tag_layout, dataBean.getData().getCareTagList());
                mRecyclerView.setAdapter(channelLikeAdapter);
                channelLikeAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mChannelAdapterLisenter != null) {
                            mChannelAdapterLisenter.onItemClick(dataBean.getData().getCareTagList().get(position));
                        }
                    }
                });
            }
            break;
        }
    }

    public interface ChannelAdapterLisenter {

        void onItemClick(ChannelTagBean channelTagBean);

    }
}
