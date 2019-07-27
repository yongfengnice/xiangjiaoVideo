package com.android.baselibrary.service.bean.mine;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/5.
 */

public class NotificationBean extends BaseBean {


    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private String noticeTitle;
        private String pushTime;
        private String noticeBrief;

        public String getNoticeBrief() {
            return noticeBrief;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public String getPushTime() {
            return pushTime;
        }

        public void setNoticeBrief(String noticeBrief) {
            this.noticeBrief = noticeBrief;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public void setPushTime(String pushTime) {
            this.pushTime = pushTime;
        }


    }


}
