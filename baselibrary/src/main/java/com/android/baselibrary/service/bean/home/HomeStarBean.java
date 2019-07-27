package com.android.baselibrary.service.bean.home;

import com.android.baselibrary.service.bean.BaseBean;

import java.io.Serializable;

/**
 * Created by yongqianggeng on 2018/9/22.
 */

public class HomeStarBean extends BaseBean implements Serializable {

    private String bwh;
    private Integer playNum;
    private String cupName;
    private String name;
    private String briefContext;
    private int id;

    private String heightNum;
    private int videoNum;
    private String headpic;

    private String cup;

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeadpic() {
        return headpic;
    }

    public int getVideoNum() {
        return videoNum;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public String getBriefContext() {
        return briefContext;
    }

    public String getBwh() {
        return bwh;
    }

    public String getCupName() {
        return cupName;
    }

    public String getHeightNum() {
        return heightNum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public void setBriefContext(String briefContext) {
        this.briefContext = briefContext;
    }

    public void setBwh(String bwh) {
        this.bwh = bwh;
    }

    public void setCupName(String cupName) {
        this.cupName = cupName;
    }

    public void setHeightNum(String heightNum) {
        this.heightNum = heightNum;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
    }

    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
    }
}
