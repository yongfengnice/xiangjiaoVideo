package com.baby.app.modules.home.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.HomeClassBean;
import com.baby.app.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yongqianggeng on 2018/10/1.
 * 最多播放
 */

public class ClassTopAdapter extends BaseQuickAdapter<HomeClassBean,BaseViewHolder> {

    private Set<String>stringSets = new HashSet<>();

    private ClassTopAdapterLisenter mClassTopAdapterLisenter;
    private ClassTopAdapterStarLisenter mClassTopAdapterStarLisenter;

    public void setClassTopAdapterStarLisenter(ClassTopAdapterStarLisenter mClassTopAdapterStarLisenter) {
        this.mClassTopAdapterStarLisenter = mClassTopAdapterStarLisenter;
    }

    public void setClassTopAdapterLisenter(ClassTopAdapterLisenter mClassTopAdapterLisenter) {
        this.mClassTopAdapterLisenter = mClassTopAdapterLisenter;
    }

    public ClassTopAdapter(int layoutResId, @Nullable List<HomeClassBean> data) {
        super(layoutResId, data);
    }

    public Set<String> getStringSets() {
        return stringSets;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeClassBean item) {

       TextView nameView = helper.getView(R.id.top_class_name_view);
        String name = "";
        for (String tempName : stringSets) {
            name = tempName;
        }
        if (item.getName().equals(name)) {
            nameView.setTextColor(Color.parseColor("#FFFA7334"));
            helper.getView(R.id.top_class_back_view).setBackgroundResource(R.drawable.home_select_corners);
        } else {
            nameView.setTextColor(Color.parseColor("#000000"));
            helper.getView(R.id.top_class_back_view).setBackgroundResource(R.color.transparent);
        }

        helper.setText(R.id.top_class_name_view,item.getName());

        helper.getView(R.id.top_class_back_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringSets.clear();
                stringSets.add(item.getName());
                notifyDataSetChanged();
                if (mClassTopAdapterLisenter != null) {
                    mClassTopAdapterLisenter.setClass(String.valueOf(item.getId()));
                }
                if (mClassTopAdapterStarLisenter != null) {
                    mClassTopAdapterStarLisenter.setClass(item.getName(),item.getValue());
                }
            }
        });

    }

    public interface ClassTopAdapterLisenter {
        void setClass(String classId);
    }

    public interface ClassTopAdapterStarLisenter {
        void setClass(String name,String value);
    }
}
