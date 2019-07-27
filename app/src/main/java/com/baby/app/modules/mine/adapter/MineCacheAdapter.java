package com.baby.app.modules.mine.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.mine.HistoryBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;
import com.baby.app.service.DownLoadInfo;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/11/8.
 */

public class MineCacheAdapter extends BaseQuickAdapter<DownLoadInfo,BaseViewHolder> {
    public MineCacheAdapter(int layoutResId, @Nullable List<DownLoadInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DownLoadInfo item) {
        ImageView histroy_img_view = helper.getView(R.id.histroy_img_view);
        TextView histroy_tex_view = helper.getView(R.id.histroy_tex_view);
        GlideUtils
                .getInstance()
                .LoadNewContextBitmap(mContext,
                        item.getCover(),
                        histroy_img_view,
                        R.mipmap.video_cover,
                        R.mipmap.video_cover,
                        GlideUtils.LOAD_BITMAP);
        histroy_tex_view.setText(item.getName());
    }
}
