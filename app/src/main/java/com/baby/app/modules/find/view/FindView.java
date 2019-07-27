package com.baby.app.modules.find.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.find.FindBean;

/**
 * Created by yongqianggeng on 2018/10/9.
 */

public interface FindView extends BaseView {

    void refreshList(FindBean findBean);
    void showNetError();
    void isCanDown(FindBean.Data findBean);
}
