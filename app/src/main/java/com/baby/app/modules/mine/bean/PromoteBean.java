package com.baby.app.modules.mine.bean;

import com.android.baselibrary.recycleradapter.entity.MultiItemEntity;
import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/10/10.
 */

public class PromoteBean extends BaseBean implements MultiItemEntity {

    public static final int PROMOT_ROW1 = 1;
    public static final int PROMOT_ROW2 = 2;
    public static final int PROMOT_ROW3 = 3;
    public static final int PROMOT_ROW4 = 4;
    public static final int PROMOT_ROW5 = 5;

    private int itemType;

    public PromoteBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
