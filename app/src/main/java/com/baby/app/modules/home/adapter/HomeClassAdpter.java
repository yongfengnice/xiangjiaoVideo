package com.baby.app.modules.home.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.HomeClassBean;
import com.android.baselibrary.util.GlideUtils;
import com.android.baselibrary.util.StringUtils;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/22.
 *
 */

public class HomeClassAdpter extends BaseQuickAdapter<HomeClassBean,BaseViewHolder> {


    public HomeClassAdpter(int layoutResId, @Nullable List<HomeClassBean> data) {
        super(layoutResId, data);
    }

    private HomeClassAdpterLisenter mHomeClassAdpterLisenter;

    public void setmHomeClassAdpterLisenter(HomeClassAdpterLisenter homeClassAdpterLisenter){
        this.mHomeClassAdpterLisenter = homeClassAdpterLisenter;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeClassBean item) {
        ImageView iv_image = helper.getView(R.id.iv_home_class_function);
        helper.setText(R.id.tv_home_function, item.getName());
        GlideUtils
                .getInstance()
                .LoadNewContextBitmap(mContext,
                        item.getClassifyIcon(),
                        iv_image,
                        R.mipmap.loading,
                        R.mipmap.loading,
                        GlideUtils.LOAD_BITMAP);

        helper.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHomeClassAdpterLisenter != null) {
                    mHomeClassAdpterLisenter.onItemClick(item);
                }
            }
        });
    }

    public interface HomeClassAdpterLisenter {

        void onItemClick(HomeClassBean classBean);
    }
}
