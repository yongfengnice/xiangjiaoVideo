package com.baby.app.modules.home.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.home.HomeDataBean;

/**
 * Created by yongqianggeng on 2018/10/8.
 */

public interface HomeView extends BaseView {

    void refreshHomeData(HomeDataBean dataBean);

}
