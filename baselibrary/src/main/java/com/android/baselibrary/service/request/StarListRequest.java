package com.android.baselibrary.service.request;

/**
 * Created by yongqianggeng on 2018/10/14.
 */

public class StarListRequest {

    private int pageNum;
    private String cup;
    private String newVideo;
    private String videoNum;

    private String cupName;

    public String getCupName() {
        return cupName;
    }

    public void setCupName(String cupName) {
        this.cupName = cupName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public String getCup() {
        return cup;
    }

    public String getNewVideo() {
        return newVideo;
    }

    public String getVideoNum() {
        return videoNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setNewVideo(String newVideo) {
        this.newVideo = newVideo;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public void setVideoNum(String videoNum) {
        this.videoNum = videoNum;
    }

    public void morePage(){
        this.pageNum ++;
    }
}
