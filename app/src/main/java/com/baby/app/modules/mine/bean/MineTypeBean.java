package com.baby.app.modules.mine.bean;

import com.android.baselibrary.recycleradapter.entity.MultiItemEntity;
import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/9/29.
 */

public class MineTypeBean extends BaseBean implements MultiItemEntity {
    //顶部
    public static final int LAYOUT_MINE_1 = 1;
    //中间
    public static final int LAYOUT_MINE_2 = 2;
    //底部
    public static final int LAYOUT_MINE_3 = 3;

    //1.1新增
    public static final int LAYOUT_MINE_4 = 4;


    private int itemType;

    public MineTypeBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
