package com.android.baselibrary.service.bean.user;


import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by gyq on 2016/7/7.
 *
 */
public class LoginBean extends BaseBean {

//    private int viewNum;
//    private int cacheNum;
    private int loginType;
    private List<BannerBean>bannerList;
    private String token;
    private List<Notice>noticeList;

    public List<Notice> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    public int getViewNum() {
//        return viewNum;
//    }
//
//    public int getCacheNum() {
//        return cacheNum;
//    }

    public int getLoginType() {
        return loginType;
    }

    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

//    public void setCacheNum(int cacheNum) {
//        this.cacheNum = cacheNum;
//    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public void setViewNum(int viewNum) {
//        this.viewNum = viewNum;
    }

    public class BannerBean {

        private String picUrl;
        private String linkType;
        private String linkTypeName;
        private String linkUrl;
        private String tagName;
        private String tagId;

        public String getTagName() {
            return tagName;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getTagId() {
            return tagId;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public String getLinkType() {
            return linkType;
        }

        public String getLinkTypeName() {
            return linkTypeName;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setLinkType(String linkType) {
            this.linkType = linkType;
        }

        public void setLinkTypeName(String linkTypeName) {
            this.linkTypeName = linkTypeName;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }

    public class Notice {
        private int id;
        private String noticleTitle;
        private String noticeBrief;
        private String noticContent;

        public String getNoticContent() {
            return noticContent;
        }

        public String getNoticeBrief() {
            return noticeBrief;
        }

        public int getId() {
            return id;
        }

        public String getNoticleTitle() {
            return noticleTitle;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setNoticContent(String noticContent) {
            this.noticContent = noticContent;
        }

        public void setNoticeBrief(String noticeBrief) {
            this.noticeBrief = noticeBrief;
        }

        public void setNoticleTitle(String noticleTitle) {
            this.noticleTitle = noticleTitle;
        }
    }
}
