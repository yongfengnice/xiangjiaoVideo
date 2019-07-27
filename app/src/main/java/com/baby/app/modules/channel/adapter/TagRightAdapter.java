package com.baby.app.modules.channel.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.baby.app.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yongqianggeng on 2018/10/4.
 * 右边
 */

public class TagRightAdapter extends BaseQuickAdapter<ChannelTagBean,BaseViewHolder> {

    private TagRightAdapterLisenter mTagRightAdapterLisenter;

    public void setTagRightAdapterLisenter(TagRightAdapterLisenter tagRightAdapterLisenter){
        this.mTagRightAdapterLisenter = tagRightAdapterLisenter;
    }

    private Set<String> stringSets = new HashSet<>();
    public Set<String> getStringSets() {
        return stringSets;
    }

    public TagRightAdapter(int layoutResId, @Nullable List<ChannelTagBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ChannelTagBean item) {
        RelativeLayout backView = helper.getView(R.id.tag_sub_class_back_view);
        TextView textView = helper.getView(R.id.tag_sub_class_name_view);
        if (stringSets.contains(item.getId()+"")) {
            textView.setTextColor(0xFFFA7334);
            backView.setBackgroundResource(R.drawable.home_select_corners);
        } else {
            textView.setTextColor(0xFF000000);
            backView.setBackgroundResource(R.color.transparent);
        }

        textView.setText(item.getName());
        if (item.getName().length() >=5) {
            textView.setTextSize(12);
        } else {
            textView.setTextSize(13);
        }

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringSets.contains(item.getId()+"")) {
                    stringSets.remove(item.getId()+"");
                    if (mTagRightAdapterLisenter != null) {
                        mTagRightAdapterLisenter.unSelectedSubClass(item);
                    }
                } else {
                    stringSets.add(item.getId()+"");
                    if (mTagRightAdapterLisenter != null) {
                        mTagRightAdapterLisenter.selectedSubClass(item);
                    }
                }
                notifyDataSetChanged();


            }
        });
    }

    public interface TagRightAdapterLisenter {
        void selectedSubClass(ChannelTagBean tagSubClassBean);
        void unSelectedSubClass(ChannelTagBean tagSubClassBean);
    }
}
