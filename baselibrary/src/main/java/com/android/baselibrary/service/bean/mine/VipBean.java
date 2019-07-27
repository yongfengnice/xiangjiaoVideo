package com.android.baselibrary.service.bean.mine;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/8.
 */

public class VipBean extends BaseBean {

    private String memberVipInfo;

    public String getMemberVipInfo() {
        return memberVipInfo;
    }

    public void setMemberVipInfo(String memberVipInfo) {
        this.memberVipInfo = memberVipInfo;
    }

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private String price;
        private String cardTypeName;
        private int cardType;
        private int id;
        private String describe;
        private int cacheNum;

        public int getId() {
            return id;
        }

        public int getCacheNum() {
            return cacheNum;
        }

        public int getCardType() {
            return cardType;
        }

        public String getCardTypeName() {
            return cardTypeName;
        }

        public String getDescribe() {
            return describe;
        }

        public String getPrice() {
            return price;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setCacheNum(int cacheNum) {
            this.cacheNum = cacheNum;
        }

        public void setCardType(int cardType) {
            this.cardType = cardType;
        }

        public void setCardTypeName(String cardTypeName) {
            this.cardTypeName = cardTypeName;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }
}
