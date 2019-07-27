package com.android.baselibrary.service.bean.find;

import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.video.VideoDetailBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/29.
 */

public class FindBean extends BaseBean {

    private int pages;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    private Info extensionInfo;
    public Info getExtensionInfo() {
        return extensionInfo;
    }

    public void setExtensionInfo(Info extensionInfo) {
        this.extensionInfo = extensionInfo;
    }
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
    public class Info{
        private String extensionUrl;
        private String extensionContext;

        public String getExtensionUrl() {
            return extensionUrl;
        }

        public String getExtensionContext() {
            return extensionContext;
        }

        public void setExtensionUrl(String extensionUrl) {
            this.extensionUrl = extensionUrl;
        }

        public void setExtensionContext(String extensionContext) {
            this.extensionContext = extensionContext;
        }
    }
    public class Data {
       private String briefContent;
       private String videoUrl;
       private String videoName;
       private String playNum;
       private String videoCover;

       private String isCare;
       private int id;
       private String classifyName;
       private String tags;

       public int getId() {
           return id;
       }

       public String getVideoUrl() {
           return videoUrl;
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

       public String getBriefContent() {
           return briefContent;
       }

       public String getClassifyName() {
           return classifyName;
       }

       public String getIsCare() {
           return isCare;
       }

       public String getTags() {
           return tags;
       }

       public void setId(int id) {
           this.id = id;
       }

       public void setPlayNum(String playNum) {
           this.playNum = playNum;
       }

       public void setVideoUrl(String videoUrl) {
           this.videoUrl = videoUrl;
       }

       public void setVideoName(String videoName) {
           this.videoName = videoName;
       }

       public void setVideoCover(String videoCover) {
           this.videoCover = videoCover;
       }

       public void setBriefContent(String briefContent) {
           this.briefContent = briefContent;
       }

       public void setClassifyName(String classifyName) {
           this.classifyName = classifyName;
       }

       public void setIsCare(String isCare) {
           this.isCare = isCare;
       }

       public void setTags(String tags) {
           this.tags = tags;
       }
   }
}

