package com.baby.app.modules.mine.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.mine.NotificationBean;

public interface INoticeView extends BaseView {

    void refresh(NotificationBean notificationBean);

}
