package com.android.baselibrary.service.bean.home;

import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/10/9.
 */

public class HomeBannerBean extends BaseBean {

    private String linkTypeName;
    private String picUrl;
    private String linkUrl;
    private int linkType;
    private int id;
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

    public int getId() {
        return id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getLinkTypeName() {
        return linkTypeName;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public void setLinkTypeName(String linkTypeName) {
        this.linkTypeName = linkTypeName;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }
}
