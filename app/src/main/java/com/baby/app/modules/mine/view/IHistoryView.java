package com.baby.app.modules.mine.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.mine.MyHistoryBean;

/**
 * Created by yongqianggeng on 2018/10/22.
 */

public interface IHistoryView extends BaseView {

    void refresh(MyHistoryBean myHistoryBean);
    void deleteSuccess(int count);
}
