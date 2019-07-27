package com.baby.app.modules.video.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.HomeListBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/3.
 */

public class VideoNvAdapter extends BaseQuickAdapter<HomeListBean,BaseViewHolder> {
    public VideoNvAdapter(int layoutResId, @Nullable List<HomeListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListBean item) {
        ImageView imageView = helper.getView(R.id.v_n_img_view);
        TextView textView = helper.getView(R.id.v_n_text_view);
        GlideUtils
                .getInstance()
                .LoadNewContextBitmap(mContext,
                        item.getVideoCover(),
                        imageView,
                        R.mipmap.video_cover,
                        R.mipmap.video_cover,
                        GlideUtils.LOAD_BITMAP);
        textView.setText(item.getVideoName());
    }
}
