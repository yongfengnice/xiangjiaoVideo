package com.baby.app.modules.channel.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.TagClassBean;
import com.baby.app.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yongqianggeng on 2018/10/4.
 * 左边
 */

public class TagLeftAdapter extends BaseQuickAdapter<TagClassBean.Data,BaseViewHolder> {

    private TagLeftAdapterLisenter mTagLeftAdapterLisenter;

    private Set<String> stringSets = new HashSet<>();
    public Set<String> getStringSets() {
        return stringSets;
    }

    public void setmTagLeftAdapterLisenter(TagLeftAdapterLisenter mTagLeftAdapterLisenter) {
        this.mTagLeftAdapterLisenter = mTagLeftAdapterLisenter;
    }

    public TagLeftAdapter(int layoutResId, @Nullable List<TagClassBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TagClassBean.Data item) {
        RelativeLayout backView = helper.getView(R.id.tag_class_back_view);
        RelativeLayout left_tag_item = helper.getView(R.id.left_tag_item);
        View redView = helper.getView(R.id.left_red_view);
        TextView textView = helper.getView(R.id.tag_class_name_view);
        if (stringSets.contains(item.getName())) {
            redView.setVisibility(View.VISIBLE);
            redView.setBackgroundColor(Color.parseColor("#FFFA7334"));
            left_tag_item.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            textView.setTextColor(Color.parseColor("#FFFA7334"));
        } else {
            redView.setVisibility(View.INVISIBLE);
            redView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            left_tag_item.setBackgroundColor(Color.parseColor("#FFF6F6F6"));
            textView.setTextColor(Color.parseColor("#FF000000"));
        }

        textView.setText(item.getName());

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringSets.clear();
                stringSets.add(item.getName());
                notifyDataSetChanged();
                mTagLeftAdapterLisenter.onClick(item);
            }
        });

    }

    public interface TagLeftAdapterLisenter {
        void onClick(TagClassBean.Data data);
    }
}
