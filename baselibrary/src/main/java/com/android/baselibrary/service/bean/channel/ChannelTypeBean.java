package com.android.baselibrary.service.bean.channel;

import com.android.baselibrary.recycleradapter.entity.MultiItemEntity;
import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/28.
 */

public class ChannelTypeBean extends BaseBean implements MultiItemEntity {
    // 标签
    public static final int LAYOUT_TAG = 1;

    public static final int LAYOUT_LIKE = 2;

    private int itemType;

    public ChannelTypeBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
