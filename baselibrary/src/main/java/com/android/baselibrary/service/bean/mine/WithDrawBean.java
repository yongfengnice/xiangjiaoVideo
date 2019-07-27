package com.android.baselibrary.service.bean.mine;

import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/12/4.
 */

public class WithDrawBean extends BaseBean {

    private int currentCronNum;
    private int totalCronNum;
    private String chargeFee;
    private double price;

    private String mostPrice;
    private String mostCron;

    public double getPrice() {
        return price;
    }

    public int getCurrentCronNum() {
        return currentCronNum;
    }

    public int getTotalCronNum() {
        return totalCronNum;
    }

    public String getChargeFee() {
        return chargeFee;
    }

    public String getMostCron() {
        return mostCron;
    }

    public String getMostPrice() {
        return mostPrice;
    }

    public void setChargeFee(String chargeFee) {
        this.chargeFee = chargeFee;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCurrentCronNum(int currentCronNum) {
        this.currentCronNum = currentCronNum;
    }

    public void setMostCron(String mostCron) {
        this.mostCron = mostCron;
    }

    public void setMostPrice(String mostPrice) {
        this.mostPrice = mostPrice;
    }

    public void setTotalCronNum(int totalCronNum) {
        this.totalCronNum = totalCronNum;
    }
}
