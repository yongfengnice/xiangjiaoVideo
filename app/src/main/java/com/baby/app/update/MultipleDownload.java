package com.baby.app.update;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.baselibrary.AppCommon;
import com.android.baselibrary.PermissionUtils;
import com.android.baselibrary.service.bean.UpdateBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.widget.dialog.UpdateDialog;
import com.baby.app.R;
import com.android.baselibrary.PermissonCallBack;
import com.android.baselibrary.base.BaseApplication;
import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.multithreaddownload.DownLoadListener;
import com.android.baselibrary.multithreaddownload.DownLoadManager;
import com.android.baselibrary.multithreaddownload.DownLoadService;
import com.android.baselibrary.multithreaddownload.TaskInfo;
import com.android.baselibrary.multithreaddownload.dbcontrol.FileHelper;
import com.android.baselibrary.multithreaddownload.dbcontrol.bean.SQLDownLoadInfo;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.util.EventBusUtil;
import com.android.baselibrary.widget.toast.ToastUtil;
import java.io.File;
import java.util.ArrayList;
/**
 * Auth：saky on 16-3-24 16:01
 * Email：saky0542@126.com
 */
public class MultipleDownload extends BasePresenter implements DownLoadListener {

    final private String MY_MV_APP = "mymv";

    private Activity mContext;

    /* 更新进度条 */
    private ProgressBar mProgress;

    //下载对话框
    private Dialog mDownloadDialog;
    TextView persent;
    private int versionCode;
    private String versionName;

    private String downloadUrl;

    Dialog noticeDialog;

    private String TASK_ID = "mymv";

    TaskInfo taskInfo = new TaskInfo();

    public boolean forceUpdate = false;

    /*使用DownLoadManager时只能通过DownLoadService.getDownLoadManager()的方式来获取下载管理器，不能通过new DownLoadManager()的方式创建下载管理器*/
    private DownLoadManager downLoadManager;

    int versionCodeNative;

    public MultipleDownload(Context context) {
        mContext = (Activity) context;
        noticeDialog = new Dialog(mContext, R.style.common_dialog);

        EventBusUtil.registerEvent(mContext);
    }

    public void checkedVersion() {

            requestDateNew(NetService.getInstance().getVersionNew(),"", new BaseCallBack() {
                @Override
                public void onSuccess(Object obj) {
                    UpdateBean updateBean = (UpdateBean)obj;
                    if (updateBean.getVersionInfo().getIsNew().equals("1")) {
                        return;
                    }

//                    updateBean.getData().setUpdate_type(0);
                    //TODO:对比本地版本和最新版本
                    String currentVersion = BaseApplication.getInstance().getAppVersionName();
                    String newVersion = updateBean.getVersionInfo().getVersionCode();

                    String versionCode = updateBean.getVersionInfo().getVersionCode();
                    if (versionCode.contains("V")) {
                        versionCode = versionCode.replace("V","");
                    }
                    UserStorage.getInstance().setVersionCode(versionCode);
                    //转换为数组
                    String[]currentVs = currentVersion.split("\\.");
                    String[]newVs = versionCode.split("\\.");
                    Boolean isUpdate = false;
                    if (currentVersion.equals(newVersion)) {
                        isUpdate = false;
                    } else {
                        if (currentVs.length < newVs.length) {
                            Boolean isFlag = false; //判断当前版本中是否存在大于最新版本的字段
                            for (int i=0;i<currentVs.length;i++) {
                                int curentVer = Integer.parseInt(currentVs[i]);
                                int newVer = Integer.parseInt(newVs[i]);
                                if (curentVer < newVer) {
                                    isUpdate = true;
                                    isFlag = false;
                                    break;
                                } else if (curentVer == newVer) {
                                    continue;
                                } else {
                                    isFlag = true;
                                }
                            }
                            if (!isFlag) {
                                isUpdate = true;
                            }


                        } else {
                            Boolean isFlag = false; //判断最新版本中是否存在大于当前版本的字段
                            for (int i=0;i<newVs.length;i++) {
                                int curentVer = Integer.parseInt(currentVs[i]);
                                int newVer = Integer.parseInt(newVs[i]);
                                if (newVer < curentVer) {
                                    isFlag = false;
                                    break;
                                } else if (curentVer == newVer) {
                                    continue;
                                } else {
                                    isUpdate = true;
                                    isFlag = true;
                                }
                            }
                            if (isFlag) {
                                isUpdate = true;
                            }
                        }
                    }

                    if (isUpdate) {
                        versionCode = versionCode.replaceAll("\\.","");
                        update(updateBean);
                    }
                }
                @Override
                public void onFaild(Object obj) {

                }
                @Override
                public void onNetWorkError(String errorMsg) {

                }
            });
    }

    @Override
    protected void showMsgFailed(BaseBean bean) {
//        super.showMsgFailed(bean);
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog(boolean forceUpdate, String versionName) {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setCancelable(false);

        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress_layout, null);
        mProgress = (ProgressBar) v.findViewById(R.id.progress);
        TextView qingshaohou = (TextView) v.findViewById(R.id.qingshaohou);
        qingshaohou.setText("新版本下载" + versionName);
        persent = (TextView) v.findViewById(R.id.persent);
        builder.setView(v);
        // 取消更新
        if (!forceUpdate) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    // 设置取消状态
                    downLoadManager.deleteTask(TASK_ID);
                }
            });
        }

        mDownloadDialog = builder.create();
        mDownloadDialog.show();

        // 启动新线程下载软件
        if (downLoadManager == null) {
            downLoadManager = new DownLoadManager(mContext);
        }
        ArrayList<TaskInfo> taskInfolist = downLoadManager.getAllTask();
        if (taskInfolist != null && taskInfolist.size() > 0) {
            taskInfo = taskInfolist.get(0);
            persent.setText(taskInfo.getProgress() + "%");
            mProgress.setProgress(taskInfo.getProgress());
            downLoadManager.startTask(taskInfo.getTaskID());
        } else {
            TASK_ID = MY_MV_APP + versionCode;
            taskInfo.setFileName(TASK_ID);
        /*服务器一般会有个区分不同文件的唯一ID，用以处理文件重名的情况*/
            taskInfo.setTaskID(TASK_ID);
            taskInfo.setOnDownloading(true);
        /*将任务添加到下载队列，下载器会自动开始下载*/
            int state = downLoadManager.addTask(TASK_ID, downloadUrl, MY_MV_APP+"_" + versionCode + ".apk");
            if (state == -1) {
                installApk();
                if (!this.forceUpdate) {
                    mDownloadDialog.dismiss();
                }
//
            }
        }
        downLoadManager.setAllTaskListener(this);

    }

    @Override
    public void onStart(SQLDownLoadInfo sqlDownLoadInfo) {
        Log.e("test", "**********sqlDownLoadInfo=" + sqlDownLoadInfo.getTaskID());

    }

    @Override
    public void onProgress(SQLDownLoadInfo sqlDownLoadInfo, boolean isSupportBreakpoint) {
        if (taskInfo.getTaskID().equals(sqlDownLoadInfo.getTaskID())) {
            taskInfo.setDownFileSize(sqlDownLoadInfo.getDownloadSize());
            taskInfo.setFileSize(sqlDownLoadInfo.getFileSize());
            persent.setText(taskInfo.getProgress() + "%");
            mProgress.setProgress(taskInfo.getProgress());
            if(downLoadManager !=null){
                downLoadManager.saveDownloadInfo(sqlDownLoadInfo.getTaskID());
            }
        }
    }

    @Override
    public void onStop(SQLDownLoadInfo sqlDownLoadInfo, boolean isSupportBreakpoint) {
        mContext.stopService(new Intent(mContext, DownLoadService.class));
        if (downLoadManager != null) {
            downLoadManager.stopAllTask();
            downLoadManager = null;
        }

    }

    @Override
    public void onError(SQLDownLoadInfo sqlDownLoadInfo) {
        if (taskInfo.getTaskID().equals(sqlDownLoadInfo.getTaskID())) {
            taskInfo.setOnDownloading(false);
        }
        ToastUtil.showToast("更新失败");
    }

    @Override
    public void onSuccess(SQLDownLoadInfo sqlDownLoadInfo) {
        Log.e("test", "******onSuccess***********");
        installApk();
        if (!this.forceUpdate) {
            mDownloadDialog.dismiss();
        }
        downLoadManager.deleteTask(taskInfo.getTaskID());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
             /*获取下载管理器*/
            downLoadManager = DownLoadService.getDownLoadManager();
            if (downLoadManager != null) {
            /*设置用户ID，客户端切换用户时可以显示相应用户的下载任务*/
            //就是这一句代码，坑了我一下午
//
            /*断点续传需要服务器的支持，设置该项时要先确保服务器支持断点续传功能*/
                downLoadManager.setSupportBreakpoint(true);
            }
        }
    };


    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(FileHelper.getFileDefaultPath() + "/(" +
                FileHelper.filterIDChars(taskInfo.getTaskID()) + ")" + MY_MV_APP+"_" + versionCode + ".apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(mContext, "com.baby.app.FileProvider", apkfile);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(apkfile);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    private void update(UpdateBean update) {
//        UpdateAppEvent event = new UpdateAppEvent();
//        event.setUpdateFlag(1);
//        EventBusUtil.postEventByEventBus(event, UpdateAppEvent.TAG);
        this.versionName = update.getVersionInfo().getVersionCode();
        if (update.getVersionInfo().getIsUpdate() == 0) {
            this.forceUpdate = false;
        } else {
            this.forceUpdate = true;
        }

        this.downloadUrl = update.getVersionInfo().getVersionUrl();
       showNoticeDialog(update,forceUpdate);

    }

    //打开存储权限
    public void checkStorage(final Context mContext,final PermissonCallBack callBack){
        //大于api 23 检测权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            PermissionUtils.checkPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionUtils.PermissionCheckCallBack() {
                @Override
                public void onHasPermission() {
                    callBack.hasPermisson();
                }

                @Override
                public void onUserHasAlreadyTurnedDown(String... permission) {
                    PermissionUtils.requestPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE,13);
                    callBack.noHasPermisson();
                }

                @Override
                public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                    callBack.noHasPermissionAndReject();
                }
            });

        } else {
            callBack.hasPermisson();
        }
    }

    private void showNoticeDialog(UpdateBean update, final Boolean isUpdate){

        try {
            AppCommon.showUpdateDialog(mContext, "更新" + update.getVersionInfo().getVersionCode() + "版本", update.getVersionInfo().getVersionContent(), isUpdate, "更新", "跳过", new UpdateDialog.UpdateDialogDialogButtonClickLisener() {
                @Override
                public void click(final UpdateDialog dialog, int which) {
                    checkStorage(mContext, new PermissonCallBack() {
                        @Override
                        public void hasPermisson() {
                            if (isUpdate) {

                            } else {
                                dialog.dismiss();
                            }
                            mContext.startService(new Intent(mContext, DownLoadService.class));
                            //下载管理器需要启动一个Service,在刚启动应用的时候需要等Service启动起来后才能获取下载管理器，所以稍微延时获取下载管理器
                            handler.sendEmptyMessageDelayed(1, 50);
                            if (mDownloadDialog != null && mDownloadDialog.isShowing()) {
                                mDownloadDialog.dismiss();
                            }
                            showDownloadDialog(forceUpdate, versionName);
                        }

                        @Override
                        public void noHasPermisson() {
                            ToastUtil.showToast("请打开存储权限");
                        }

                        @Override
                        public void noHasPermissionAndReject() {
                            ToastUtil.showToast("获取读取权限失败");

                        }
                    });
                }
            });
        } catch (Exception e){

        }
    }

    public Boolean isShowDialog() {
        if ((noticeDialog != null && noticeDialog.isShowing()) || (mDownloadDialog != null && mDownloadDialog.isShowing())) {
            return true;
        }
        return false;
    }

    @Override
    protected BaseView getView() {
        return null;
    }
}
