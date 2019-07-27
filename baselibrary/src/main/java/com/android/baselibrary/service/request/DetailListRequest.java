package com.android.baselibrary.service.request;

/**
 * Created by yongqianggeng on 2018/10/13.
 */

public class DetailListRequest {
    /**
     * 根据明星ID、分类、标签获取影片列表-S
     * mostCare 最多喜欢
     * newVideo 最新播放
     * mostPlay 最多播放
     * starId	否	String	明星ID
     classifyId	否	String	分类ID
     tagId	否	String	标签ID
     tagIds	否	String	标签ID集合，多个标签以逗号分隔
     pageNum	否	int	页数
     tagName	否	String	标签名称
     */
    private int pageNum = 1;

    private int mostCare = 0;
    private int newVideo = 0;
    private int mostPlay = 0;
    private String starId;
    private String classifyId;
    private String tagId;
    private String tagIds;
    private String tagName;

    public String getClassifyId() {
        return classifyId;
    }

    public int getMostCare() {
        return mostCare;
    }

    public int getMostPlay() {
        return mostPlay;
    }

    public int getNewVideo() {
        return newVideo;
    }

    public int getPageNum() {
        return pageNum;
    }

    public String getStarId() {
        return starId;
    }

    public String getTagId() {
        return tagId;
    }

    public String getTagIds() {
        return tagIds;
    }

    public String getTagName() {
        return tagName;
    }

    public void setStarId(String starId) {
        this.starId = starId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public void setMostCare(int mostCare) {
        this.mostCare = mostCare;
    }

    public void setMostPlay(int mostPlay) {
        this.mostPlay = mostPlay;
    }

    public void setNewVideo(int newVideo) {
        this.newVideo = newVideo;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void morePage(){
        this.pageNum++;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
