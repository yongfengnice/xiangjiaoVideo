package com.android.baselibrary.service.bean.video;

import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/10/3.
 * 猜你喜欢
 */

public class VideoLikeBean extends BaseBean {

    private int id;
    private String videoName;
    private String tagsName;
    private String playNum;
    private String videoCover;

    public int getId() {
        return id;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public String getPlayNum() {
        return playNum;
    }

    public String getTagsName() {
        return tagsName;
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

    public void setPlayNum(String playNum) {
        this.playNum = playNum;
    }

    public void setTagsName(String tagsName) {
        this.tagsName = tagsName;
    }
}
