package com.baby.app.modules.home.adapter;

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
 * Created by yongqianggeng on 2018/9/24.
 */

public class HomeListAdapter extends BaseQuickAdapter<HomeListBean,BaseViewHolder> {

    public HomeListAdapter(int layoutResId, @Nullable List<HomeListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListBean item) {
        ImageView imageView = helper.getView(R.id.iv_home_function);
        TextView textView = helper.getView(R.id.tv_home_function);
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
