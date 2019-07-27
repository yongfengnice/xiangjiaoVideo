package com.baby.app.modules.home.bean;

import com.android.baselibrary.recycleradapter.entity.MultiItemEntity;
import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/10/16.
 */

public class StartDetailTypeBean extends BaseBean implements MultiItemEntity {

    public static final int LAYOUT_STAR_DEAIL_1 = 1;
    public static final int LAYOUT_STAR_DEAIL_2 = 2;
    public static final int LAYOUT_STAR_DEAIL_3 = 3;

    private int itemType;

    public StartDetailTypeBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
