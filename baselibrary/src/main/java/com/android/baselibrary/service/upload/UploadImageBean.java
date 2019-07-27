package com.android.baselibrary.service.upload;

import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2017/12/11.
 * 图片bean
 */

public class UploadImageBean extends BaseBean {

    private String data;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
