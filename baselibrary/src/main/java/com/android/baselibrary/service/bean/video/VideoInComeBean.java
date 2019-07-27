package com.android.baselibrary.service.bean.video;

import com.android.baselibrary.service.bean.BaseBean;

import java.io.Serializable;

/**
 * Created by yongqianggeng on 2018/10/13.
 */

public class VideoInComeBean extends BaseBean implements Serializable {

    private int isCache = 0;
    private int id;
    private String videoName;
    private String videoUrl;

    public int getIsCache() {
        return isCache;
    }

    public void setIsCache(int isCache) {
        this.isCache = isCache;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public int getId() {
        return id;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setId(int id) {
        this.id = id;
    }
}
