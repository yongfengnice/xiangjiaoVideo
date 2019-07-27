package com.android.baselibrary.service.bean.home;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/14.
 */

public class StarDataBean extends BaseBean {

    private int pages;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    private List<HomeClassBean>cupList;

    private List<HomeStarBean>data;

    public List<HomeStarBean> getData() {
        return data;
    }

    public void setData(List<HomeStarBean> data) {
        this.data = data;
    }

    public List<HomeClassBean> getCupList() {
        return cupList;
    }

    public void setCupList(List<HomeClassBean> cupList) {
        this.cupList = cupList;
    }
}
