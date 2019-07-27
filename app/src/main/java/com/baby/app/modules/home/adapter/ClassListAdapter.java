package com.baby.app.modules.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.home.HomeListBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/1.
 *
 */

public class ClassListAdapter extends BaseQuickAdapter<DetailListBean.Data,BaseViewHolder> {

    public ClassListAdapter(int layoutResId, @Nullable List<DetailListBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailListBean.Data item) {
        ImageView imageView = helper.getView(R.id.iv_class_function);
        TextView textView = helper.getView(R.id.tv_class_function);
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
