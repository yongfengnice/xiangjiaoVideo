package com.android.baselibrary.service.bean.mine;

import com.android.baselibrary.service.bean.BaseBean;

/**
 * Created by yongqianggeng on 2018/10/19.
 */

public class ExtensionBean extends BaseBean {

    private String extensionUrl;
    private String extensionCode;
    private String extensionContext;

    public String getExtensionContext() {
        return extensionContext;
    }

    public void setExtensionContext(String extensionContext) {
        this.extensionContext = extensionContext;
    }

    public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }

    public String getExtensionUrl() {
        return extensionUrl;
    }

    public void setExtensionUrl(String extensionUrl) {
        this.extensionUrl = extensionUrl;
    }
}
