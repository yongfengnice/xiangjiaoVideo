package com.baby.app.service;
interface DownLoadProgress {
    void progress(long curduration ,long allduration);// 下载进度
    void downStatus(int status);// 下载状态0：暂停 1：开始 2:已完成3:下载出错
}
