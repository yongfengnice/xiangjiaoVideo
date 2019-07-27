package com.baby.app.modules.home.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.home.DetailListBean;

/**
 * Created by yongqianggeng on 2018/10/13.
 */

public interface DetailListView extends BaseView {

    void refreshList(DetailListBean listBean);
}
