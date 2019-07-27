package com.baby.app.modules.video.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.video.VideoCommentBean;
import com.android.baselibrary.util.GlideUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/4.
 */

public class VideoCommentAdapter extends BaseQuickAdapter<VideoCommentBean.Data,BaseViewHolder> {
    public VideoCommentAdapter(int layoutResId, @Nullable List<VideoCommentBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoCommentBean.Data item) {
        RoundedImageView imageView = helper.getView(R.id.v_user_img_view);
        TextView v_user_name_view = helper.getView(R.id.v_user_name_view);
        ImageView sex_img_view = helper.getView(R.id.sex_img_view);
        TextView time_text_view = helper.getView(R.id.time_text_view);
        TextView comment_content_view = helper.getView(R.id.comment_content_view);
        GlideUtils
                .getInstance()
                .LoadContextCircleBitmap(mContext,
                        item.getHeadpic(),
                        imageView,
                        R.mipmap.ic_head_l,
                        R.mipmap.ic_head_l);
        v_user_name_view.setText(item.getNickName());
        time_text_view.setText(item.getComTime());
        comment_content_view.setText(item.getComContent());
    }
}
