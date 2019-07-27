package com.android.baselibrary.service.bean.user;

import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.mine.HistoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/8.
 * 用户信息
 */

public class UserBean extends BaseBean implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String qqurl;
        private MemberInfo memberInfo;
        private List<HistoryBean>viewHistoryList;
        private List<HistoryBean>careHistoryList;

        public List<HistoryBean> getCareHistoryList() {
            return careHistoryList;
        }

        public void setCareHistoryList(List<HistoryBean> careHistoryList) {
            this.careHistoryList = careHistoryList;
        }

        public MemberInfo getMemberInfo() {
            return memberInfo;
        }

        public List<HistoryBean> getViewHistoryList() {
            return viewHistoryList;
        }

        public void setViewHistoryList(List<HistoryBean> viewHistoryList) {
            this.viewHistoryList = viewHistoryList;
        }

        public String getQqurl() {
            return qqurl;
        }

        public void setQqurl(String qqurl) {
            this.qqurl = qqurl;
        }

        public void setMemberInfo(MemberInfo memberInfo) {
            this.memberInfo = memberInfo;
        }
    }

    public class MemberInfo {
        private int usedViewNum;
        private int usedCacheNum;
        private String nickName;
        private int sex;
        private String nexName;

        private String extensionCode;
        private String headpic;
        private int cacheNum;
        private int isVip;
        private int ccExNum;

        private int sortNo;
        private int nextSortNo;
        private int viewNum;
        private int exNum;
        private String levleName;

        private float percentExNum;
        private int isRemind;
        private String tel;

        private int id;
        private String vipDate;

        public int getCacheNum() {
            return cacheNum;
        }

        public int getViewNum() {
            return viewNum;
        }

        public int getId() {
            return id;
        }

        public float getPercentExNum() {
            return percentExNum;
        }

        public int getCcExNum() {
            return ccExNum;
        }

        public int getExNum() {
            return exNum;
        }

        public int getIsRemind() {
            return isRemind;
        }

        public int getIsVip() {
            return isVip;
        }

        public int getNextSortNo() {
            return nextSortNo;
        }

        public int getSex() {
            return sex;
        }

        public int getSortNo() {
            return sortNo;
        }

        public int getUsedCacheNum() {
            return usedCacheNum;
        }

        public int getUsedViewNum() {
            return usedViewNum;
        }

        public String getExtensionCode() {
            return extensionCode;
        }

        public String getHeadpic() {
            return headpic;
        }

        public String getLevleName() {
            return levleName;
        }

        public String getNexName() {
            return nexName;
        }

        public String getNickName() {
            return nickName;
        }

        public String getTel() {
            return tel;
        }

        public String getVipDate() {
            return vipDate;
        }

        public void setViewNum(int viewNum) {
            this.viewNum = viewNum;
        }

        public void setCacheNum(int cacheNum) {
            this.cacheNum = cacheNum;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCcExNum(int ccExNum) {
            this.ccExNum = ccExNum;
        }

        public void setExNum(int exNum) {
            this.exNum = exNum;
        }

        public void setExtensionCode(String extensionCode) {
            this.extensionCode = extensionCode;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public void setIsRemind(int isRemind) {
            this.isRemind = isRemind;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        public void setLevleName(String levleName) {
            this.levleName = levleName;
        }

        public void setNexName(String nexName) {
            this.nexName = nexName;
        }

        public void setNextSortNo(int nextSortNo) {
            this.nextSortNo = nextSortNo;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public void setPercentExNum(float percentExNum) {
            this.percentExNum = percentExNum;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setUsedCacheNum(int usedCacheNum) {
            this.usedCacheNum = usedCacheNum;
        }

        public void setUsedViewNum(int usedViewNum) {
            this.usedViewNum = usedViewNum;
        }

        public void setVipDate(String vipDate) {
            this.vipDate = vipDate;
        }
    }

}
