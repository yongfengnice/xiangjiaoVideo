package com.baby.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yongqianggeng on 2019/2/2.
 */

public class Time {

    private static long prevTime=0;
    private static long curTime=0;
    public static boolean check(){
        String time = "2019-7-14 01:00:00";
        Date curDate = new Date(System.currentTimeMillis());
//PROCESSING
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        try {
            Date endDate = sdf.parse(time);
            long diff = curDate.getTime() - endDate.getTime();
            if (diff > 0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
            // TODO Auto-generated catch block e.printStackTrace();
        }
    }

    // 将字符串转为时间戳
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        }catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return re_time;
    }

}
