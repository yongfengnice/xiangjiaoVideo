package com.baby.app.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.hdl.m3u8.M3U8InfoManger;
import com.hdl.m3u8.M3U8LiveManger;
import com.hdl.m3u8.bean.M3U8;
import com.hdl.m3u8.bean.OnM3U8InfoListener;

import java.io.File;
import java.util.List;

/**
 * 用于在后台下载的service
 * <p>
 * Created by ltb on 2018/11/1.
 */

public class DownLoadServer extends Service {
    private String dirPath;
    private DownLoadProgress dProgress;
    private AnalysisComplete aComplete;
    private long itemSize =0;
    private int total =0;
    private long mcurTs = 0;
    private MyM3U8DownLoadTask task1;
    private String downId;
    @Override
    public void onCreate() {
        super.onCreate();
        dirPath = getRootDirPath(getApplicationContext());
    }

    private IDownLoadServer.Stub onBinder = new IDownLoadServer.Stub() {
        @Override
        public void start(String url, String name, String cover, String videoId) throws RemoteException {
            doDown(url, name, cover, videoId);
        }

        @Override
        public void pause(String id,String url) throws RemoteException {
            if (url.endsWith("mp4")) {
                PRDownloader.pause(Integer.parseInt(id));
            } else if (url.endsWith("m3u8")) {
                if(null != task1){
                    task1.stop();
                }
            }
        }

        @Override
        public void error(String url) throws RemoteException {

        }

        @Override
        public void delete(String url) throws RemoteException {

        }

        @Override
        public void resume(String id,String url) throws RemoteException {
            if (task1 == null) {
                if (url.endsWith("mp4")) {
                    PRDownloader.resume(Integer.parseInt(id));
                } else if (url.endsWith("m3u8")) {
                    DownInfoModel model = new DownInfoModel();
                    String path = model.findByDownId(id);
//                    String taskId = model.findTaskIdByDownId(id);
                    if(TextUtils.isEmpty(path)) {
                        path = dirPath + "/" + System.currentTimeMillis() + ".ts";
                    }
                    itemSize = model.finditemFileSizeByDownId(id);
                    total = model.findTotalTsByDownId(id);
                    onDownload(url,path,id,model.finditemFileSizeByDownId(id),model.findTotalTsByDownId(id),model.findCurTsByDownId(id));
                }
            }

        }

        @Override
        public void setProgress(DownLoadProgress downLoadProgress) throws RemoteException {
            dProgress = downLoadProgress;
        }

        @Override
        public void setAnalysis(AnalysisComplete analysisComplete) throws RemoteException {
            aComplete = analysisComplete;
        }
    };

    /**
     * 开始下载
     *
     * @param url
     */
    private void doDown(final String url, final String name, final String cover, final String videoId) {
        if (TextUtils.isEmpty(url)) {
            showToast("无效的视屏路径");
            return;
        }

        final DownInfoModel model = new DownInfoModel();
        if(model.findByStatus()){
            showToast("其他视频正在下载");
            return;
        }
        List<DownLoadInfo> downLoadInfoList = model.findByUrl(url);
        if (null != downLoadInfoList && downLoadInfoList.size() > 0) {
            showToast("该视频已在缓存列表里");
        } else {// 该文件没有被下载过
            if (url.endsWith("mp4")) {
                int downId = downMp4(url, name + ".mp4");
                DownLoadInfo downLoadInfo = new DownLoadInfo();
                downLoadInfo.setDownId(String.valueOf(downId));
                downLoadInfo.setUrl(url);
                downLoadInfo.setVideoId(videoId);
                downLoadInfo.setCover(cover);
                downLoadInfo.setName(name);
                downLoadInfo.setStatus(1);
                downLoadInfo.setPath(dirPath + "/" + name + ".mp4");
                model.save(downLoadInfo);
            } else if (url.endsWith("m3u8")) {
                analysisM3u8(url, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        String path = dirPath + "/" + System.currentTimeMillis() + ".ts";
                        String downId = String.valueOf(System.currentTimeMillis());
                        onDownload(url,path,downId,0,0,0);
                        DownLoadInfo downLoadInfo = new DownLoadInfo();
                        downLoadInfo.setDownId(downId);
                        downLoadInfo.setUrl(url);
                        downLoadInfo.setVideoId(videoId);
                        downLoadInfo.setCover(cover);
                        downLoadInfo.setName(name);
                        downLoadInfo.setStatus(1);
                        downLoadInfo.setPath(path);
                        model.save(downLoadInfo);
                    }
                });
            }
        }
    }

    /**
     * 下载MP4文件
     *
     * @param url
     * @param name
     */
    private int downMp4(String url, String name) {
        final DownInfoModel model = new DownInfoModel();
        return PRDownloader.download(url, dirPath, name)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume(String url) {
                        showToast("开始下载");
                        model.updateStatusByUrl(url, 1);
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause(String url) {
                        try {
                            showToast("暂停下载");
                            model.updateStatusByUrl(url, 0);
                            if (null != dProgress)
                                dProgress.downStatus(0);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress, String url) {
                        try {
                            model.updateProgressByUrl(url, progress.currentBytes, progress.totalBytes);
                            if (null != dProgress)
                                dProgress.progress(progress.currentBytes, progress.totalBytes);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete(String url) {
                        try {
                            model.updateStatusByUrl(url, 2);
                            if (null != dProgress)
                                dProgress.downStatus(2);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Error error, String url) {
                        try {
                            model.updateStatusByUrl(url, 3);
                            if (null != dProgress)
                                dProgress.downStatus(3);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 解析m3u8文件
     *
     * @param url
     */
    private void analysisM3u8(final String url, final Handler handler) {
        M3U8InfoManger.getInstance().getM3U8Info(url, new OnM3U8InfoListener() {
            @Override
            public void onSuccess(M3U8 m3U8) {// 解析成功
                showToast("开始下载");
                if (null != aComplete) {
                    try {

                        handler.sendEmptyMessageDelayed(0, 3000);
                        aComplete.analysis(url);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onError(Throwable errorMsg) {
                showToast("解析视屏失败");
            }
        });
    }

    /**
     * 下载m3u8文件
     *
     * @param url
     */
    public void onDownload(final String url,String path,String taskId,long itemFileSize,int totalTs,long curTs) {
        final DownInfoModel model = new DownInfoModel();
        if (taskId == null) {
            taskId = "0";
        } else if (taskId.equals("")) {
            taskId = "0";
        }
        task1 = new MyM3U8DownLoadTask(taskId,itemFileSize,totalTs,curTs);
        task1.setSaveFilePath(path);
        task1.download(url, new MyOnDownloadLisetner() {
            @Override
            public void onDownloading(long itemFileSize, int totalTs, long curTs) {
                itemSize = itemFileSize;
                total = totalTs;
                mcurTs = curTs;
            }

            @Override
            public void onSuccess() {
                try {
                    showToast("下载完成");
                    model.updateStatusByUrl(url, 2);
                    if (null != dProgress)
                        dProgress.downStatus(2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onProgress(long curLength) {
                try {

                    model.updateProgressByUrl(url, curLength, itemSize*total);
                    if (task1!=null) {
                        model.updateDataByUrl(url,itemSize,total,curLength);
                    }
                    if (null != dProgress)
                        dProgress.progress(curLength, itemSize*total);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onError(Throwable errorMsg) {
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return onBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        try {
            DownInfoModel model = new DownInfoModel();
            List<DownLoadInfo> infos = model.findIdByStatus();
            if(null != infos && infos.size()>0){
                for(DownLoadInfo info:infos){
                    PRDownloader.pause(Integer.parseInt(info.getDownId()));
                }
            }
            if(null != task1)
                task1.stop();
            model.updateStatus();
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
                    null)[0];
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

    /**
     * service里弹toast
     *
     * @param msg
     */
    private void showToast(final String msg) {
        Handler handlerThree = new Handler(Looper.getMainLooper());
        handlerThree.post(new Runnable() {
            @SuppressLint("WrongConstant")
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

}
