package com.baby.app.modules.video.iview;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.video.VideoCommentBean;
import com.android.baselibrary.service.bean.video.VideoDetailBean;

/**
 * Created by yongqianggeng on 2018/10/17.
 */

public interface IVideoView extends BaseView {
    void refreshDetail(VideoDetailBean videoDetailBean);
    void refreshComment(VideoCommentBean videoCommentBean);

    void dontPlayVideo();

    void isCanDown();

    void onCacheFailed();


}
