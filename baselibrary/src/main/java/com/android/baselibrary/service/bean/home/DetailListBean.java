package com.android.baselibrary.service.bean.home;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/13.
 */

public class DetailListBean extends BaseBean {

    private List<Data>data;
    private List<HomeClassBean>classifyList;
    public List<HomeClassBean> getClassifyList() {
        return classifyList;
    }

    private int pages;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setClassifyList(List<HomeClassBean> classifyList) {
        this.classifyList = classifyList;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private String briefContent;
        private int starId;
        private int careNum;
        private String videoUrl;
        private String createTime;
        private String videoName;
        private String playNum;
        private String classifyId;
        private String videoCover;
        private int id;
        private String classifyName;
        private String tags;

        public int getId() {
            return id;
        }

        public int getCareNum() {
            return careNum;
        }

        public int getStarId() {
            return starId;
        }

        public String getBriefContent() {
            return briefContent;
        }

        public String getClassifyId() {
            return classifyId;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getPlayNum() {
            return playNum;
        }

        public String getTags() {
            return tags;
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

        public void setBriefContent(String briefContent) {
            this.briefContent = briefContent;
        }

        public void setCareNum(int careNum) {
            this.careNum = careNum;
        }

        public void setClassifyId(String classifyId) {
            this.classifyId = classifyId;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setPlayNum(String playNum) {
            this.playNum = playNum;
        }

        public void setStarId(int starId) {
            this.starId = starId;
        }

        public void setTags(String tags) {
            this.tags = tags;
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
}
