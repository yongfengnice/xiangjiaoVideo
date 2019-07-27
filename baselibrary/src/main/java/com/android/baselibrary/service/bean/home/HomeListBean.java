package com.android.baselibrary.service.bean.home;

import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/9/22.
 */

public class HomeListBean extends BaseBean {

    private String videoUrl;
    private String videoName;
    private String videoCover;
    private int id;

    public int getId() {
        return id;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
