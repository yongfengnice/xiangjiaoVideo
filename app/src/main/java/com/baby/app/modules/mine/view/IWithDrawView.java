package com.baby.app.modules.mine.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.mine.WithDrawBean;

/**
 * Created by yongqianggeng on 2018/12/4.
 */

public interface IWithDrawView extends BaseView {

    void callBack(WithDrawBean withDrawBean);

    void withDrawSuccess();

}
