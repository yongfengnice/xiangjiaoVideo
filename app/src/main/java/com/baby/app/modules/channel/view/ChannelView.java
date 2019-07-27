package com.baby.app.modules.channel.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.channel.ChannelDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.channel.ChannelTagDataBean;
import com.android.baselibrary.service.bean.channel.TagClassBean;

/**
 * Created by yongqianggeng on 2018/10/13.
 */

public interface ChannelView extends BaseView {

    void refresh(ChannelDataBean channelDataBean);

    void refreshTagClass(TagClassBean tagClassBean);

    void refreshChannelTag(ChannelTagDataBean channelTagDataBean);
}
