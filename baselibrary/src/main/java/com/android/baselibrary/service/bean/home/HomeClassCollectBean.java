package com.android.baselibrary.service.bean.home;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/9.
 */

public class HomeClassCollectBean extends BaseBean {
    private List<HomeListBean>videoList;
    private int id;
    private String name;

    public int getId() {
        return id;
    }


    public List<HomeListBean> getVideoList() {
        return videoList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVideoList(List<HomeListBean> videoList) {
        this.videoList = videoList;
    }
}
