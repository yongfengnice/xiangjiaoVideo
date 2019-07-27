package com.android.baselibrary.service.bean.video;

import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.home.HomeBannerBean;
import com.android.baselibrary.service.bean.home.HomeListBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/17.
 */

public class VideoDetailBean extends BaseBean {

    private String useView;
    private String useCache;


    private Data data;

    public String getUseCache() {
        return useCache;
    }

    public String getUseView() {
        return useView;
    }

    public void setUseCache(String useCache) {
        this.useCache = useCache;
    }

    public void setUseView(String useView) {
        this.useView = useView;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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
        private Info extensionInfo;
        private String briefContent;
        private int starId;
        private String isLike;
        private String cent;
        private String briefContext;
        private int starVideoNum;
        private String videoUrl;
        private List<HomeBannerBean>bannerListHead;
        private List<HomeBannerBean>bannerList;
        private String videoName;

        private String starName;
        private int id;
        private String heightNum;
        private String pushTime;
        private String classifyName;

        private List<HomeListBean>starVideoList;

        private String playNum;
        private int dislikeNum;

        private String headPic;
        private String tags;
        private int videoCommentNum;
        private int careNum;
        private String bwh;
        private List<VideoLikeBean>likeVideoList;
        private String videoCover;
        private String isCare;
        private int loseNum;
        private String cup;

        public List<HomeBannerBean> getBannerListHead() {
            return bannerListHead;
        }

        public void setBannerListHead(List<HomeBannerBean> bannerListHead) {
            this.bannerListHead = bannerListHead;
        }

        public Info getExtensionInfo() {
            return extensionInfo;
        }

        public void setExtensionInfo(Info extensionInfo) {
            this.extensionInfo = extensionInfo;
        }

        public String getPlayNum() {
            return playNum;
        }

        public String getVideoCover() {
            return videoCover;
        }

        public String getVideoName() {
            return videoName;
        }

        public int getId() {
            return id;
        }

        public String getCup() {
            return cup;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public String getTags() {
            return tags;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public String getBriefContent() {
            return briefContent;
        }

        public int getStarId() {
            return starId;
        }

        public int getCareNum() {
            return careNum;
        }

        public List<HomeBannerBean> getBannerList() {
            return bannerList;
        }

        public int getDislikeNum() {
            return dislikeNum;
        }

        public int getLoseNum() {
            return loseNum;
        }

        public int getStarVideoNum() {
            return starVideoNum;
        }

        public int getVideoCommentNum() {
            return videoCommentNum;
        }

        public List<HomeListBean> getStarVideoList() {
            return starVideoList;
        }

        public List<VideoLikeBean> getLikeVideoList() {
            return likeVideoList;
        }

        public String getBriefContext() {
            return briefContext;
        }

        public String getBwh() {
            return bwh;
        }

        public String getCent() {
            return cent;
        }

        public String getHeadPic() {
            return headPic;
        }

        public String getHeightNum() {
            return heightNum;
        }

        public String getIsCare() {
            return isCare;
        }

        public String getIsLike() {
            return isLike;
        }

        public String getPushTime() {
            return pushTime;
        }

        public String getStarName() {
            return starName;
        }

        public void setPlayNum(String playNum) {
            this.playNum = playNum;
        }

        public void setVideoCover(String videoCover) {
            this.videoCover = videoCover;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCup(String cup) {
            this.cup = cup;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public void setStarId(int starId) {
            this.starId = starId;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public void setCareNum(int careNum) {
            this.careNum = careNum;
        }

        public void setBriefContent(String briefContent) {
            this.briefContent = briefContent;
        }

        public void setBannerList(List<HomeBannerBean> bannerList) {
            this.bannerList = bannerList;
        }

        public void setBriefContext(String briefContext) {
            this.briefContext = briefContext;
        }

        public void setBwh(String bwh) {
            this.bwh = bwh;
        }

        public void setCent(String cent) {
            this.cent = cent;
        }

        public void setDislikeNum(int dislikeNum) {
            this.dislikeNum = dislikeNum;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public void setHeightNum(String heightNum) {
            this.heightNum = heightNum;
        }

        public void setIsCare(String isCare) {
            this.isCare = isCare;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
        }

        public void setLikeVideoList(List<VideoLikeBean> likeVideoList) {
            this.likeVideoList = likeVideoList;
        }

        public void setLoseNum(int loseNum) {
            this.loseNum = loseNum;
        }

        public void setPushTime(String pushTime) {
            this.pushTime = pushTime;
        }

        public void setStarName(String starName) {
            this.starName = starName;
        }

        public void setStarVideoList(List<HomeListBean> starVideoList) {
            this.starVideoList = starVideoList;
        }

        public void setStarVideoNum(int starVideoNum) {
            this.starVideoNum = starVideoNum;
        }

        public void setVideoCommentNum(int videoCommentNum) {
            this.videoCommentNum = videoCommentNum;
        }
    }




}
