package com.baby.app.modules.mine.bean;

import android.text.SpannableString;
import android.text.Spanned;

import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/10/10.
 */

public class PromoteFuliBean extends BaseBean {

    private String title;
    private Spanned spanned;
    private SpannableString subTitle;

    private boolean isTopHidden;
    private boolean isBottomHidden;

    public void setSpanned(Spanned spanned) {
        this.spanned = spanned;
    }

    public Spanned getSpanned() {
        return spanned;
    }

    public boolean getIsBottomHidden() {
        return isBottomHidden;
    }

    public boolean getIsTopHidden() {
        return isTopHidden;
    }

    public void setIsBottomHidden(boolean isBottomHidden) {
        this.isBottomHidden = isBottomHidden;
    }

    public void setIsTopHidden(boolean isTopHidden) {
        this.isTopHidden = isTopHidden;
    }

    public String getTitle() {
        return title;
    }

    public SpannableString getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(SpannableString subTitle) {
        this.subTitle = subTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
