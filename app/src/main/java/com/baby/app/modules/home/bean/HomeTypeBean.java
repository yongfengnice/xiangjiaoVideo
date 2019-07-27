package com.baby.app.modules.home.bean;

import com.android.baselibrary.recycleradapter.entity.MultiItemEntity;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.home.HomeClassCollectBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/22.
 */

public class HomeTypeBean extends BaseBean implements MultiItemEntity {

    // 功能
    public static final int LAYOUT_CLASS = 1001;
    //最新片源
    public static final int LAYOUT_NEW_LIST = 1002;
    //重磅热播
    public static final int LAYOUT_HOT_LIST = 1003;
    //人气明星
    public static final int LAYOUT_MAN_LIST = 1004;

    private HomeClassCollectBean homeClassCollectBean;

    public HomeClassCollectBean getHomeClassCollectBean() {
        return homeClassCollectBean;
    }

    public void setHomeClassCollectBean(HomeClassCollectBean homeClassCollectBean) {
        this.homeClassCollectBean = homeClassCollectBean;
    }

    private int itemType;

    public HomeTypeBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
