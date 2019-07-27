package com.baby.app.service;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 下载信息类
 * Created by ltb on 2018/11/1.
 */
@Table(name = "down_info")
public class DownLoadInfo {

    @Column(name = "edit")//
    private boolean edit;


    //当前下载完成的文件个数
    @Column(name = "curTs")//
            long curTs;

    //总文件的个数
    @Column(name = "totalTs")//
            int totalTs;
    //单个文件的大小
    @Column(name = "itemFileSize")
    long itemFileSize;

    @Column(name = "id", isId = true)
    public int id;
    @Column(name = "name")// 名称
    String name;
    @Column(name = "task_id")//任务id
            String taskId;
    @Column(name = "down_id")// 下载id
    String downId;
    @Column(name = "down_status")// 下载状态0：暂停 1：开始 2:已完成3:下载出错 4 删除
    int status;
    @Column(name = "down_path")// 下载到的路径
    String path;
    @Column(name = "down_url")// 源文件路径
    String url;
    @Column(name = "progress")// 下载进度
    String progress;
    @Column(name = "currentBytes")// 當前大小
    long currentBytes;
    @Column(name = "totalBytes")// 总大小
    long totalBytes;
    @Column(name = "cover")// 封面
            String cover;
    @Column(name = "videoId")// 视屏id
            String videoId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }



    public long getItemFileSize() {
        return itemFileSize;
    }

    public void setItemFileSize(long itemFileSize) {
        this.itemFileSize = itemFileSize;
    }

    public int getTotalTs() {
        return totalTs;
    }

    public long getCurTs() {
        return curTs;
    }

    public void setTotalTs(int totalTs) {
        this.totalTs = totalTs;
    }

    public void setCurTs(long curTs) {
        this.curTs = curTs;
    }

    public boolean getEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownId() {
        return downId;
    }

    public void setDownId(String downId) {
        this.downId = downId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public long getCurrentBytes() {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
