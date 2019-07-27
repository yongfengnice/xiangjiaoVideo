package com.baby.app.modules.video.bean;

import com.android.baselibrary.recycleradapter.entity.MultiItemEntity;
import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/10/2.
 */

public class VideoTypeBean extends BaseBean implements MultiItemEntity {

    public static final int LAYOUT_VIDEO_1 = 1;
    public static final int LAYOUT_VIDEO_2 = 2;
    public static final int LAYOUT_VIDEO_3 = 3;
    public static final int LAYOUT_VIDEO_4 = 4;
    public static final int LAYOUT_VIDEO_5 = 5;

    private int itemType;

    public VideoTypeBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
