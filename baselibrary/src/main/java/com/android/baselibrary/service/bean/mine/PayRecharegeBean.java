package com.android.baselibrary.service.bean.mine;

import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/10/19.
 */

public class PayRecharegeBean extends BaseBean {

    private String payUrl;
    private String tradeNo;

    public String getPayUrl() {
        return payUrl;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }
}
