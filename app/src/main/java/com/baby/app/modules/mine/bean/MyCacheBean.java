package com.baby.app.modules.mine.bean;

import com.android.baselibrary.recycleradapter.entity.MultiItemEntity;
import com.android.baselibrary.service.bean.BaseBean;
import com.baby.app.service.DownLoadInfo;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/4.
 */

public class MyCacheBean extends BaseBean implements MultiItemEntity {

    public static final int LAYOUT_SECTION = 1;// 已经缓存
    public static final int LAYOUT_ROW = 2;// 未缓存
    private List<DownLoadInfo> downLoadInfoList;

    public List<DownLoadInfo> getDownLoadInfoList() {
        return downLoadInfoList;
    }

    public void setDownLoadInfoList(List<DownLoadInfo> downLoadInfoList) {
        this.downLoadInfoList = downLoadInfoList;
    }

    private int itemType;

    public MyCacheBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
