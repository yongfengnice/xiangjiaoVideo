package com.android.baselibrary.service.bean.mine;

import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/10/18.
 */

public class HistoryBean extends BaseBean {

    private int id;
    private int videoId;
    private String videoCover;
    private String videoName;
    private String viewTime;

    private boolean selected;

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getVideoId() {
        return videoId;
    }

    public int getId() {
        return id;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public String getViewTime() {
        return viewTime;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public void setViewTime(String viewTime) {
        this.viewTime = viewTime;
    }
}


