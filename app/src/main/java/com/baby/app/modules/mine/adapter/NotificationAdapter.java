package com.baby.app.modules.mine.adapter;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.mine.NotificationBean;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.baby.app.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/5.
 * 通知
 */

public class NotificationAdapter extends BaseQuickAdapter<NotificationBean.Data,BaseViewHolder> {


    public NotificationAdapter(int layoutResId, @Nullable List<NotificationBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NotificationBean.Data item) {
        TextView notice_title_view = helper.getView(R.id.notice_title_view);
        TextView notice_time_view = helper.getView(R.id.notice_time_view);
        TextView notice_content_view = helper.getView(R.id.notice_content_view);
        notice_title_view.setText(item.getNoticeTitle());
        notice_content_view.setText(item.getNoticeBrief());
        notice_time_view.setText(item.getPushTime());
    }
}
