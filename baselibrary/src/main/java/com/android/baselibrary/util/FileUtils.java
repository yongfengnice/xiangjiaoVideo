package com.android.baselibrary.util;

/**
 * Created by gyq on 2017/2/16.
 */

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * Title: FileUtils.java
 * Description: 对sd卡的文件相关操作
 * @author Liusong
 * @date 2015-1-12
 * @version V1.0
 */
public class FileUtils {

    /**
     * 判断sdcrad是否已经安装
     * @return boolean true安装 false 未安装
     */
    public static boolean isSDCardMounted(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 得到sdcard的路径
     * @return
     */
    public static String getSDCardRoot(){
//        System.out.println(isSDCardMounted()+Environment.getExternalStorageState());
        if(isSDCardMounted()){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }
    /**
     * 创建文件的路径及文件
     * @param path 路径，方法中以默认包含了sdcard的路径，path格式是"/path...."
     * @param filename 文件的名称
     * @return 返回文件的路径，创建失败的话返回为空
     */
    public static String createMkdirsAndFiles(String path, String filename) {
        if (TextUtils.isEmpty(path)) {
//            throw new RuntimeException("路径为空");
            return null;
        }
        path = getSDCardRoot()+path;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
//                throw new RuntimeException("创建文件夹不成功");

                e.printStackTrace();
                return null;
            }
        }
        File f = new File(file, filename);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f.getAbsolutePath();
    }

    /**
     * 把内容写入文件
     * @param path 文件路径
     * @param text 内容
     */
    public static void write2File(String path,String text,boolean append){
        BufferedWriter bw = null;
        try {
            //1.创建流对象
            bw = new BufferedWriter(new FileWriter(path,append));
            //2.写入文件
            bw.write(text);
            //换行刷新
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //4.关闭流资源
            if(bw!= null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 删除文件
     * @param path
     * @return
     */
    public static boolean deleteFile(String path){
        if(TextUtils.isEmpty(path)){
//            throw new RuntimeException("路径为空");
            return false;
        }
        File file = new File(path);
        if(file.exists()){
            try {
                file.delete();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
