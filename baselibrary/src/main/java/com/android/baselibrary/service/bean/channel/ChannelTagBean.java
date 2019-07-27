package com.android.baselibrary.service.bean.channel;

import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/9/28.
 */

public class ChannelTagBean extends BaseBean {

    private String name;
    private String picUrl;
    private int id;

    public int getId() {
        return id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
