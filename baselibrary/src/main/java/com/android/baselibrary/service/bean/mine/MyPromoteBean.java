package com.android.baselibrary.service.bean.mine;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/20.
 */

public class MyPromoteBean extends BaseBean {

    private int toIncome;
    private int iTotalDisplayRecords;

    public int getToIncome() {
        return toIncome;
    }

    public void setToIncome(int toIncome) {
        this.toIncome = toIncome;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    private List<Data>data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private String nickName;
        private String tel;
        private String regeditTime;

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public void setRegeditTime(String regeditTime) {
            this.regeditTime = regeditTime;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getNickName() {
            return nickName;
        }

        public String getRegeditTime() {
            return regeditTime;
        }

        public String getTel() {
            return tel;
        }



        private String rechargeDate;
        private Double rechargePrice;
        private int income;
        private String name;

        public Double getRechargePrice() {
            return rechargePrice;
        }

        public int getIncome() {
            return income;
        }



        public String getRechargeDate() {
            return rechargeDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setIncome(int income) {
            this.income = income;
        }

        public void setRechargeDate(String rechargeDate) {
            this.rechargeDate = rechargeDate;
        }

        public void setRechargePrice(Double rechargePrice) {
            this.rechargePrice = rechargePrice;
        }

    }

}
