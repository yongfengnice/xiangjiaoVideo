package com.android.baselibrary.service.bean.video;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/4.
 */

public class VideoCommentBean extends BaseBean {

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private String comTime;
        private String nickName;
        private String comContent;
        private int videoId;
        private int id;

        private int dianzNum;
        private int isMine;
        private String headpic;

        public void setId(int id) {
            this.id = id;
        }

        public void setComContent(String comContent) {
            this.comContent = comContent;
        }

        public void setComTime(String comTime) {
            this.comTime = comTime;
        }

        public void setDianzNum(int dianzNum) {
            this.dianzNum = dianzNum;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public void setIsMine(int isMine) {
            this.isMine = isMine;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public int getId() {
            return id;
        }

        public int getDianzNum() {
            return dianzNum;
        }

        public int getIsMine() {
            return isMine;
        }

        public int getVideoId() {
            return videoId;
        }

        public String getComContent() {
            return comContent;
        }

        public String getComTime() {
            return comTime;
        }

        public String getHeadpic() {
            return headpic;
        }

        public String getNickName() {
            return nickName;
        }
    }
}
