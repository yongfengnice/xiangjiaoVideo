package com.android.baselibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.util.StringUtils;
import com.android.baselibrary.widget.dialog.UpdateDialog;
import com.kaopiz.kprogresshud.KProgressHUD;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.android.baselibrary.util.SPUtils.getObject;

/**
 * Created by digua on 2015/10/27.
 *
 */
public class AppCommon {

    private static long lastClickTime = 0;

    /*
    * 防止多次点击
    * */
    public static boolean isFastDoubleClick(long times) {
        long time = System.currentTimeMillis();
        if ((System.currentTimeMillis() - lastClickTime) > times) {
            lastClickTime = time;
            return false;
        } else {
            lastClickTime = time;
            return true;
        }
    }


    public static void dialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        builder.setCancelable(false);
        builder.setTitle("提示"); //设置标题
        builder.setMessage("你的账号在其他地方登录！"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
            }
        });
        //参数都设置完成了，创建并显示出来
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        Activity activity = (Activity) context;
        if (!activity.isFinishing()) {
            dialog.show();
        }
    }

    public static void dialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        builder.setCancelable(false);
        builder.setTitle("提示"); //设置标题
        builder.setMessage(message); //设置内容
        builder.setIcon(com.android.baselibrary.R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
            }
        });
        //参数都设置完成了，创建并显示出来
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        Activity activity = (Activity) context;
        if (!activity.isFinishing()) {
            dialog.show();
        }
    }


    public static void dialogFlagRenter(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("你已经从租客变成非租客！"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        if (strTime == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date;
        try {
            date = formatter.parse(strTime);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期格式化 yyyy-MM-dd
     * 将String类型转换成date类型
     * 再将Date转换成long类型
     * 再将long类型转换成String类型 精确到秒
     */
    public static String stringToSecondString(String time, String format) {
        Date date = stringToDate(time, "yyyy-MM-dd HH:mm:ss"); // String类型转成date类型
        if (date == null) {
            return "";
        } else {
            long millSec = date.getTime(); // date类型转成long类型
            if (millSec == 0) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date1 = new Date(millSec);
            return sdf.format(date1);
        }
    }

    /**
     * 日期格式化 yyyy-MM-dd
     * 将String类型转换成date类型
     * 再将Date转换成long类型
     * 再将long类型转换成String类型 精确到天
     */
    public static String stringToDateString(String time, String format) {
        Date date = stringToDate(time, "yyyy-MM-dd"); // String类型转成date类型
        if (date == null) {
            return "";
        } else {
            long millSec = date.getTime(); // date类型转成long类型
            if (millSec == 0) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date1 = new Date(millSec);
            return sdf.format(date1);
        }
    }

    /**
     * 日期格式化
     */
    public static String getDateStr(Long millSec) {
        if (millSec == 0) {
            return "";
        }
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    public static Date getDate(Long millSec) {
        Date date = new Date(millSec * 1000);
        return date;
    }

    /**
     * 日期格式化 yyyy-MM-dd
     */
    public static String getDateStrYMD(Long millSec) {
        if (millSec == 0) {
            return "";
        }
        millSec = millSec * 1000;
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 日期格式化 yyyy.MM.dd
     */
    public static String getDateStrYMD3(Long millSec) {
        if (millSec == 0) {
            return "";
        }
        millSec = millSec * 1000;
        String format = "yyyy.MM.dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 日期格式化 yyyy.MM.dd
     */
    public static String getDateStrYMD4(Long millSec) {
        if (millSec == 0) {
            return "";
        }
        millSec = millSec * 1000;
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(millSec);
        return sdf.format(date);
    }


    /**
     * 日期格式化 yyyy.MM.dd
     */
    public static String getDateStrYMD5(Long millSec) {
        if (millSec == 0) {
            return "";
        }
        millSec = millSec / 1000;
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(millSec);
        return sdf.format(date);
    }


    /**
     * 日期格式化 yyyy-MM-dd
     */
    public static String getDateStrYMD2(Long millSec) {
        if (millSec == 0) {
            return "";
        }
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 日期格式化 自定义
     */
    public static String getDateStrFormat(Long millSec, String format) {
        if (millSec == 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 日期格式化 自定义
     */
    public static String getDateStrFormat2(Long millSec, String format) {
        if (millSec == 0) {
            return "";
        }
        millSec = millSec * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(millSec);
        return sdf.format(date);
    }


    /**
     * 计算两个日期相差几天
     */
    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int year1 = aCalendar.get(Calendar.YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int year2 = aCalendar.get(Calendar.YEAR);
        if (year1 - year2 > 0) {
            return ((year1 - year2) * 365 + (day1 - day2));
        } else {
            return day1 - day2;
        }

    }

    public static void main(String[] args) {
        try {
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate1 = dateFormat1.parse("2017-06-01");
            Date myDate2 = dateFormat1.parse("2016-06-03");
            System.out.println(daysOfTwo(myDate1, myDate2));
        } catch (Exception e) {
        }
    }

    /**
     * 初始化文件目录
     * <p/>
     * my
     * |
     * |---update
     * |
     * |---temp
     */
    public static void initFile() {
        try {
            // 目录 my
            String rootDir = Constants.SD_PATH + "/" + Constants.APP_NAME;
            File file_root = new File(rootDir);
            if (!file_root.exists()) {
                file_root.mkdirs();
            }

            // my-update
            String updateDir = Constants.SD_PATH + "/" + Constants.APP_NAME + "/" + Constants.DIR_APP_UPDATE;
            File file_update = new File(updateDir);
            if (!file_update.exists()) {
                file_update.mkdirs();
            }

            // my-temp
            String tempDir = Constants.SD_PATH + "/" + Constants.APP_NAME + "/" + Constants.DIR_APP_TEMP;
            File file_temp = new File(tempDir);
            if (!file_temp.exists()) {
                file_temp.mkdirs();
            }
            String imageDir = Constants.BASE_IMAGE_CACHE;
            File image_root = new File(imageDir);
            if (!image_root.exists()) {
                image_root.mkdirs();
            }
            // 清除temp文件夹下所有文件
            deleteAllFile(tempDir);

        } catch (Exception e) {

        }
    }

    /**
     * 清除路径下所有文件
     *
     * @param path
     */
    public static void deleteAllFile(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                file.delete();
            }
        }
    }


    /**
     * 文件转byte
     */
    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * byte转file
     */
    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 压缩图片
     *
     * @param image    原图
     * @param fileSize 压缩的图片大小限定
     * @return 压缩后图片流
     */
    public static byte[] compressImage(Bitmap image, int fileSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100; // 此处可尝试用90%开始压缩，跳过100%压缩
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        int length = baos.toByteArray().length;
        while ((length = baos.toByteArray().length) / 1024 > fileSize) {
            // 每次都减少10
            options -= 10;
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            if (options <= 0)
                options = 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        return baos.toByteArray();
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        // ByteArrayInputStream isBm = new
        // ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        // Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        // return bitmap;
    }

    /**
     * 校验null
     * <p/>
     * 如果为null，转换为""
     */
    public static String checkNull(String str) {
        if (str != null && !str.equals("null")) {
            return str;
        } else {
            return "";
        }
    }


    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     */
    public static Uri setCutUriImage(Context context, String imagePath2) {
        Uri imagePath = null;
        String status = Environment.getExternalStorageState();
        SimpleDateFormat fomater = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String timeString = fomater.format(new Date(time));
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, timeString);
        values.put(MediaStore.Images.Media.DATE_TAKEN, time);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.ORIENTATION, 90);
        if (imagePath2 != null)
            values.put(MediaStore.Images.Media.DATA, imagePath2);
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            imagePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            imagePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        return imagePath;
    }

    /**
     * Try to return the absolute file path from the given Uri  兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * 加载dialog
     *
     * @param context
     * @return
     */
    public static void createLoadingDialog(Context context, String msg) {

        /*LayoutInflater inflater = LayoutInflater.from(context);
        // 得到加载view
        View v = inflater.inflate(com.android.baselibrary.R.layout.view_tips_loading, null);
        // 加载布局
        RelativeLayout layout = (RelativeLayout) v.findViewById(com.android.baselibrary.R.id.dialog_view);
        // 创建自定义样式dialog
        Dialog loadingDialog = new Dialog(context, com.android.baselibrary.R.style.loading_dialog);
        // 可以取消
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(true);
        // 设置布局

        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        // 加载动画
        *//*AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.animset);
        layout.startAnimation(animationSet);*//*

        Animator anim0 = AnimatorInflater.loadAnimator(context, com.android.baselibrary.R.animator.animatorset);
        anim0.setTarget(layout);
        anim0.setInterpolator(new AnticipateOvershootInterpolator());
        anim0.start();*/

        //return loadingDialog;
    }


    public static KProgressHUD createDialog(Context context) {
        KProgressHUD hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setCancellable(false);

        return hud;
    }

    public static int compare(Date d1, Date d2) {
        SimpleDateFormat FORMAT = new SimpleDateFormat(
                "MM-dd HH:mm");
        String str1 = FORMAT.format(d1);
        System.out.println("str1: " + str1);
        String str2 = FORMAT.format(d2);
        System.out.println("str2: " + str2);

        int result = str1.compareTo(str2);
        if (result > 0) {
            System.out.println(str1 + " 晚于 " + str2);
            return 1;
        } else if (result == 0) {
            System.out.println(str1 + " 等于 " + str2);
            return 0;
        } else {
            System.out.println(str1 + " 早于 " + str2);
            return -1;
        }
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     * @return
     */
    public static String getStandardDate(String timeStr) {
        if(timeStr == null || timeStr.length() == 0){
            return "";
        }

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t);
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    /**
     * @param mContext   context
     * @param title      标题
     * @param message    消息
     * @param okListener 确定监听
     */
    public static void showDialog(Context mContext, String title, String message, String okText, String cancelText, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);  //先得到构造器
        builder.setCancelable(false);
        builder.setTitle(title); //设置标题
        builder.setMessage(message); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton(okText, okListener);
        builder.setNegativeButton(cancelText, new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
            }
        });
        //参数都设置完成了，创建并显示出来
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        try {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.main_theme_color));
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextColor(mContext.getResources().getColor(R.color.main_theme_color));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param mContext   context
     * @param title      标题
     * @param message    消息
     * @param okListener 确定监听
     */
    public static void showDialog(Context mContext, String title, String message, String okText, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);  //先得到构造器
        builder.setCancelable(false);
        builder.setTitle(title); //设置标题
        builder.setMessage(message); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton(okText, okListener);

        //参数都设置完成了，创建并显示出来
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        try {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.main_theme_color));
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextColor(mContext.getResources().getColor(R.color.main_theme_color));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param mContext   context
     * @param title      标题
     * @param message    消息
     */
    public static void showUpdateDialog(Context mContext, String title, String message, Boolean isUpdate, String okText, String cancelText, UpdateDialog.UpdateDialogDialogButtonClickLisener updateDialogDialogButtonClickLisener) {
        UpdateDialog.Builder builder = new UpdateDialog.Builder(mContext);
        final UpdateDialog mDialog = builder.setTitle(title)
                .setMessage(message).createTwoButtonDialog();
        if (isUpdate) {
            builder.hiddenCancel();
        }
        if (!isUpdate) {
            builder.setPositiveButton("跳过",
                    updateDialogDialogButtonClickLisener).
                    setNegativeButton("更新", updateDialogDialogButtonClickLisener).creatButton();
        } else {
            builder.setNegativeButton("更新", updateDialogDialogButtonClickLisener).creatButton();
        }

        mDialog.show();
    }

//    /**
//     * @param mContext   context
//     * @param title      标题
//     * @param message    消息
//     * @param okListener 确定监听
//     */
//    public static void showUpdateDialog(Context mContext, String title, String message, Boolean isUpdate, String okText, String cancelText, final UpdateButtonLiserner okListener) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);  //先得到构造器
//        builder.setCancelable(false);
//        builder.setTitle(title); //设置标题
//        builder.setMessage(message); //设置内容
//        builder.setPositiveButton(okText, null);
//        if (!isUpdate) {
//            builder.setNegativeButton(cancelText, new DialogInterface.OnClickListener() { //设置确定按钮
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss(); //关闭dialog
//                }
//            });
//        }
//
//        //参数都设置完成了，创建并显示出来
//        final AlertDialog dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(false);
//
//        dialog.show();
//        try {
//          Button positiceButton =  dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//          positiceButton.setTextColor(mContext.getResources().getColor(R.color.main_theme_color));
//          positiceButton.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View view) {
//                  if (okListener!=null) {
//                      okListener.onClick(dialog);
//                  }
//              }
//          });
//
//            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
//            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
//            mAlert.setAccessible(true);
//            Object mAlertController = mAlert.get(dialog);
//            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
//            mMessage.setAccessible(true);
//            TextView mMessageView = (TextView) mMessage.get(mAlertController);
//            mMessageView.setTextColor(mContext.getResources().getColor(R.color.main_theme_color));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//    }

    public static void showDialog(Context mContext, String title, String message, String okText, String cancelText, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);  //先得到构造器
        builder.setCancelable(false);
        builder.setTitle(title); //设置标题
        builder.setMessage(message); //设置内容
        builder.setIcon(com.android.baselibrary.R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton(okText, okListener);
        builder.setNegativeButton(cancelText, cancelListener);
        //参数都设置完成了，创建并显示出来
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        try {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.main_theme_color));
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextColor(mContext.getResources().getColor(R.color.main_theme_color));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static CharSequence matcherSearchText(int color, String string, String keyWord) {
        SpannableStringBuilder builder = new SpannableStringBuilder(string);
        int indexOf = string.indexOf(keyWord);
        if (indexOf != -1) {
            builder.setSpan(new ForegroundColorSpan(color), indexOf, indexOf + keyWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public static boolean isHaveContent(EditText view) {
        return StringUtils.isNotEmpty(view.getText().toString()) &&
                !view.getText().toString().replaceAll(" ", "").replaceAll("\n", "").equals("");
    }

    public static boolean isLogin() {
        LoginBean loginBean = getObject(Constants.SP_LOGIN_OBJECT, LoginBean.class);
        if (loginBean == null) {
            return false;
        } else {
            return true;
        }
    }

    public static String ToDBC(String input) {
        char[] c = new char[0];
        if(StringUtils.isNotEmpty(input)){
            c = input.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 12288) {
                    c[i] = (char) 32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375)
                    c[i] = (char) (c[i] - 65248);
            }
        }

        return new String(c);
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public interface UpdateButtonLiserner{
        void onClick(AlertDialog dialog);
    }


    @TargetApi(19)
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup decorViewGroup = (ViewGroup)activity.getWindow().getDecorView();
            //获取自己布局的根视图
            View rootView = ((ViewGroup) (decorViewGroup.findViewById(android.R.id.content))).getChildAt(0);
            //预留状态栏位置
            rootView.setFitsSystemWindows(true);

            //添加状态栏高度的视图布局，并填充颜色
            View statusBarTintView = new View(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    AppCommon.getInternalDimensionSize(activity.getResources(), "status_bar_height"));
            params.gravity = Gravity.TOP;
            statusBarTintView.setLayoutParams(params);
            statusBarTintView.setBackgroundColor(color);
            decorViewGroup.addView(statusBarTintView);
        }
    }

    public static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }




}
