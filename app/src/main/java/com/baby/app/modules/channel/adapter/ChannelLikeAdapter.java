package com.baby.app.modules.channel.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/14.
 *
 */

public class ChannelLikeAdapter extends BaseQuickAdapter<ChannelTagBean,BaseViewHolder> {
    public ChannelLikeAdapter(int layoutResId, @Nullable List<ChannelTagBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelTagBean item) {
        TextView textView = helper.getView(R.id.tv_channel_tag_view);
        textView.setText(item.getName());

        View bottomView = helper.getView(R.id.channel_bottom_view);
        bottomView.setVisibility(View.GONE);
        ImageView imageView = helper.getView(R.id.c_channel_tag_img);
        GlideUtils
                .getInstance()
                .LoadContextCircleBitmap(mContext,
                        item.getPicUrl(),
                        imageView,
                        R.mipmap.loading,
                        R.mipmap.loading);


    }
}
