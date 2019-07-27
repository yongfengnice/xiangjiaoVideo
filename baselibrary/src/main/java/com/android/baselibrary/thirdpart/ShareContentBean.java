package com.android.baselibrary.thirdpart;

import android.app.Activity;
import android.graphics.Bitmap;

/**
 * Created by gyq on 2016/8/25.
 */
public class ShareContentBean {

    private int fromType;  //从哪里进来分享

    private String keytype; //分享的唯一标识

    private Activity activity;  //上下文
    private String title;       //分享标题
    private String description; //分享描述
    private String defaultText; //分享的默认描述 --weibo有
    private String targetUrl;   //分享的url
    int thumbResID;  //分享的小图标--RES资源

    private String thumbUrl; //分享小图标--QQ设置

    private int flag; //0-分享到朋友，1-分享到朋友圈

    private Bitmap bitmap;

    public int getFromType() {
        return fromType;
    }

    public void setFromType(int fromType) {
        this.fromType = fromType;
    }

    public String getKeytype() {
        return keytype;
    }

    public void setKeytype(String keytype) {
        this.keytype = keytype;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public int getThumbResID() {
        return thumbResID;
    }

    public void setThumbResID(int thumbResID) {
        this.thumbResID = thumbResID;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
