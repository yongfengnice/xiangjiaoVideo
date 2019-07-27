package com.baby.app.modules.mine.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.mine.PayBean;
import com.android.baselibrary.service.bean.mine.PayRecharegeBean;
import com.android.baselibrary.service.bean.mine.VipBean;
import com.android.baselibrary.service.bean.mine.WithDrawBean;

/**
 * Created by yongqianggeng on 2018/10/19.
 */

public interface IVipView extends BaseView {
    void refreshVip(VipBean vipBean);
    void refreshPayType(PayBean payBean);

    void refreshPayRecharge(PayRecharegeBean payRecharegeBean);

    void dataCallBack(WithDrawBean withDrawBean);
}
