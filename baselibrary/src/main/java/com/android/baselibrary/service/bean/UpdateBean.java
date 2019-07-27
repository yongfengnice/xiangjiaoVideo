package com.android.baselibrary.service.bean;

/**
 * Created by yongqianggeng on 2018/10/21.
 */

public class UpdateBean extends BaseBean {

    private VersionInfo versionInfo;

    public VersionInfo getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(VersionInfo versionInfo) {
        this.versionInfo = versionInfo;
    }

    public class VersionInfo {
        private int id;
        private String versionCode;
        private String versionUrl;
        private int isUpdate;
        private String isNew;
        private String versionContent;

        public int getId() {
            return id;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public String getVersionUrl() {
            return versionUrl;
        }

        public int getIsUpdate() {
            return isUpdate;
        }

        public String getIsNew() {
            return isNew;
        }

        public String getVersionContent() {
            return versionContent;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public void setVersionUrl(String versionUrl) {
            this.versionUrl = versionUrl;
        }

        public void setIsNew(String isNew) {
            this.isNew = isNew;
        }

        public void setIsUpdate(int isUpdate) {
            this.isUpdate = isUpdate;
        }

        public void setVersionContent(String versionContent) {
            this.versionContent = versionContent;
        }
    }
}
