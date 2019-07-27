package com.baby.app.modules.channel.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/4.
 * 下面
 */

public class TagBottomAdapter extends BaseQuickAdapter<ChannelTagBean,BaseViewHolder> {


    private TagBottomAdapterLisenter mTagBottomAdapterLisenter;

    public void setTagBottomAdapterLisenter(TagBottomAdapterLisenter tagBottomAdapterLisenter) {
        this.mTagBottomAdapterLisenter = tagBottomAdapterLisenter;
    }

    public TagBottomAdapter(int layoutResId, @Nullable List<ChannelTagBean> data) {
        super(layoutResId, data);



    }

    @Override
    protected void convert(BaseViewHolder helper, final ChannelTagBean item) {

        RelativeLayout backView = helper.getView(R.id.tag_bottom_class_back_view);
        TextView textView = helper.getView(R.id.tag_bottom_class_name_view);
        RelativeLayout deleteBtn = helper.getView(R.id.tag_class_delete_btn);
        textView.setText(item.getName());
        if (item.getName().length() >=5) {
            textView.setTextSize(12);
        } else {
            textView.setTextSize(13);
        }
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTagBottomAdapterLisenter != null) {
                    mTagBottomAdapterLisenter.deleteSubClass(item);
                }
            }
        });

    }

    public interface TagBottomAdapterLisenter {
        void deleteSubClass(ChannelTagBean tagSubClassBean);
    }
}
