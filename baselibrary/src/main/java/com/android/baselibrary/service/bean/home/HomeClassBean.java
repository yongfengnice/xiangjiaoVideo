package com.android.baselibrary.service.bean.home;

import com.android.baselibrary.service.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/22.
 */

public class HomeClassBean extends BaseBean implements Serializable {

    //TODO:test
    private int imageResouce;

    private String title;

    private boolean selected;

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getImageResouce() {
        return imageResouce;
    }

    public void setImageResouce(int imageResouce) {
        this.imageResouce = imageResouce;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    //TODO:网络获取
    private String classifyIcon;
    private String name;
    private int id;

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassifyIcon() {
        return classifyIcon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassifyIcon(String classifyIcon) {
        this.classifyIcon = classifyIcon;
    }
}
