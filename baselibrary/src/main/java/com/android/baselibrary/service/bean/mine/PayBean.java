package com.android.baselibrary.service.bean.mine;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/19.
 */

public class PayBean extends BaseBean {

    private List<Data>data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private int payType;
        private String payImag;
        private String payTypeName;

        public int getPayType() {
            return payType;
        }

        public String getPayImag() {
            return payImag;
        }

        public String getPayTypeName() {
            return payTypeName;
        }

        public void setPayImag(String payImag) {
            this.payImag = payImag;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public void setPayTypeName(String payTypeName) {
            this.payTypeName = payTypeName;
        }
    }
}
