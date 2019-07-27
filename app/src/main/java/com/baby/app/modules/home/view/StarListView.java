package com.baby.app.modules.home.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.home.StarDataBean;

/**
 * Created by yongqianggeng on 2018/10/14.
 */

public interface StarListView extends BaseView {

    void refresh(StarDataBean starDataBean);
}
