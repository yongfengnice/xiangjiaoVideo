package com.baby.app.service;

import com.android.baselibrary.base.BaseApplication;
import com.baby.app.modules.mine.bean.MyCacheBean;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */

public class DownInfoModel {
    private DbManager db;

    public DownInfoModel() {
        db = BaseApplication.getInstance().getDb();
    }

    /**
     * 查找所有
     *
     * @return
     */
    public List<DownLoadInfo> findAll() {
        try {
            return db.selector(DownLoadInfo.class).orderBy("id", true).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据源下载路径查找
     *
     * @return
     */
    public List<DownLoadInfo> findByUrl(String url) {
        try {
            WhereBuilder builder = WhereBuilder.b();
            builder.and("down_url", "=", url);
            return db.selector(DownLoadInfo.class).where(builder).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 根据下载id查找保存路径
     *
     * @return
     */
    public String findByDownId(String id) {
        try {
            WhereBuilder builder = WhereBuilder.b();
            builder.and("down_id", "=", id);
            List<DownLoadInfo> downLoadInfos = db.selector(DownLoadInfo.class).where(builder).findAll();
            if(null != downLoadInfos && downLoadInfos.size()>0)
                return downLoadInfos.get(0).getPath();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据下载id查找保存路径
     *
     * @return
     */
    public String findVideoByDownId(String id) {
        try {
            WhereBuilder builder = WhereBuilder.b();
            builder.and("videoId", "=", id);
            List<DownLoadInfo> downLoadInfos = db.selector(DownLoadInfo.class).where(builder).findAll();
            if(null != downLoadInfos && downLoadInfos.size()>0)
                return downLoadInfos.get(0).getPath();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long finditemFileSizeByDownId(String id) {
        try {
            WhereBuilder builder = WhereBuilder.b();
            builder.and("down_id", "=", id);
            List<DownLoadInfo> downLoadInfos = db.selector(DownLoadInfo.class).where(builder).findAll();
            if(null != downLoadInfos && downLoadInfos.size()>0)
                return downLoadInfos.get(0).getItemFileSize();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long findCurTsByDownId(String id) {
        try {
            WhereBuilder builder = WhereBuilder.b();
            builder.and("down_id", "=", id);
            List<DownLoadInfo> downLoadInfos = db.selector(DownLoadInfo.class).where(builder).findAll();
            if(null != downLoadInfos && downLoadInfos.size()>0)
                return downLoadInfos.get(0).getCurTs();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int findTotalTsByDownId(String id) {
        try {
            WhereBuilder builder = WhereBuilder.b();
            builder.and("down_id", "=", id);
            List<DownLoadInfo> downLoadInfos = db.selector(DownLoadInfo.class).where(builder).findAll();
            if(null != downLoadInfos && downLoadInfos.size()>0)
                return downLoadInfos.get(0).getTotalTs();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据下载id查找保存路径
     *
     * @return
     */
    public boolean findByStatus() {
        try {
            WhereBuilder builder = WhereBuilder.b();
            builder.and("down_status", "=", 1);
            List<DownLoadInfo> downLoadInfos = db.selector(DownLoadInfo.class).where(builder).findAll();
            if(null != downLoadInfos && downLoadInfos.size()>0)
                return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据下载状态查找下载ID
     *
     * @return
     */
    public List<DownLoadInfo> findIdByStatus() {
        try {
            WhereBuilder builder = WhereBuilder.b();
            builder.and("down_status", "=", 1);
            List<DownLoadInfo> downLoadInfos = db.selector(DownLoadInfo.class).where(builder).findAll();
                return downLoadInfos;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据源下载路径更新状态
     *
     * @return
     */
    public void updateStatusByUrl(String url, int status) {
        try {
            WhereBuilder b = WhereBuilder.b();
            b.and("down_url", "=", url); //构造修改的条件
            KeyValue value = new KeyValue("down_status", status);
            db.update(DownLoadInfo.class, b, value);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateEditByUrl(String url, boolean edit) {
        try {
            WhereBuilder b = WhereBuilder.b();
            b.and("down_url", "=", url); //构造修改的条件
            KeyValue value = new KeyValue("edit", edit);
            db.update(DownLoadInfo.class, b, value);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据源下载路径更新状态
     *
     * @return
     */
    public void delete(String url) {
        try {

            db.delete(DownLoadInfo.class, WhereBuilder.b("down_url", "=", url));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 暂停所有任务
     *
     * @return
     */
    public void updateStatus() {
        try {
            WhereBuilder b = WhereBuilder.b();
            b.and("down_status", "!=", 2);
            KeyValue value = new KeyValue("down_status", 0);
            db.update(DownLoadInfo.class, b, value);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据源下载路径更新状态
     *
     * @return
     */
    public void updateProgressByUrl(String url, long currentBytes, long totalBytes) {
        try {

            WhereBuilder b = WhereBuilder.b();
            b.and("down_url", "=", url); //构造修改的条件
            KeyValue value1 = new KeyValue("currentBytes", currentBytes);
            KeyValue value2 = new KeyValue("totalBytes", totalBytes);
            db.update(DownLoadInfo.class, b, value1, value2);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据源下载路径更新taskId
     *
     * @return
     */
    public void updateDataByUrl(String url, long itemFileSize, long totalTs, long curTs) {
        try {

            WhereBuilder b = WhereBuilder.b();
            b.and("down_url", "=", url); //构造修改的条件
            KeyValue value1 = new KeyValue("itemFileSize", itemFileSize);
            KeyValue value2 = new KeyValue("totalTs", totalTs);
            KeyValue value3 = new KeyValue("curTs", curTs);
            db.update(DownLoadInfo.class, b, value1,value2,value3);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询已经完成的文件
     *
     * @return
     */
    public List<DownLoadInfo> getCachBean() {
        try {
            // 查询已经完成
            WhereBuilder b = WhereBuilder.b();
            b.and("down_status", "=", 2); // 已经完成
            List<DownLoadInfo> infos = db.selector(DownLoadInfo.class).where(b).findAll();
            return infos;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询未完成的文件
     *
     * @return
     */
    public List<DownLoadInfo> getNoCachBean() {
        try {
            WhereBuilder b = WhereBuilder.b();
            b.and("down_status", "!=", 2); // 未完成
            List<DownLoadInfo> infos = db.selector(DownLoadInfo.class).where(b).findAll();
            return infos;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存信息
     *
     * @return
     */
    public void save(DownLoadInfo info) {
        try {
            db.saveOrUpdate(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
