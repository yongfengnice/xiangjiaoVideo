package com.android.baselibrary.service.bean.mine;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/22.
 */

public class MyHistoryBean extends BaseBean {
   private List<HistoryBean>data;

    public List<HistoryBean> getData() {
        return data;
    }

    public void setData(List<HistoryBean> data) {
        this.data = data;
    }
}
