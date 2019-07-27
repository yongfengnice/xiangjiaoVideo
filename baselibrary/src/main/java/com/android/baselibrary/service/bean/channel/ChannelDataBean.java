package com.android.baselibrary.service.bean.channel;

import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.home.HomeBannerBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/13.
 * 频道首页
 */

public class ChannelDataBean extends BaseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private List<ChannelTagBean>hotTagList;
        private List<ChannelTagBean>careTagList;
        private List<HomeBannerBean>bannerList;

        public List<HomeBannerBean> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<HomeBannerBean> bannerList) {
            this.bannerList = bannerList;
        }

        public List<ChannelTagBean> getCareTagList() {
            return careTagList;
        }

        public void setCareTagList(List<ChannelTagBean> careTagList) {
            this.careTagList = careTagList;
        }

        public List<ChannelTagBean> getHotTagList() {
            return hotTagList;
        }

        public void setHotTagList(List<ChannelTagBean> hotTagList) {
            this.hotTagList = hotTagList;
        }
    }

}
