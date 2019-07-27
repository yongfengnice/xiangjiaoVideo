package com.android.baselibrary.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isNotEmpty(String str) {
        if (str != null && str.length() > 0 && !str.equals("null")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    // ArrayList类型转成String类型
    public static String arrayList2String(ArrayList<String> arrayList,String rex) {
        String result = "";
        if (arrayList != null && arrayList.size() > 0) {
            for (String item : arrayList) {
                // 把列表中的每条数据用逗号分割开来，然后拼接成字符串
                result += item + rex;
            } // 去掉最后一个逗号
            result = result.substring(0, result.length() - 1);
        } return result;
    }


    /**
     * 过滤空字符,防止报空指针
     */
    public static String filterNull(String string) {
        if (string != null && string.length() > 0 && !string.equals("null")) {
            return string;
        } else {
            return "";
        }
    }

    /*取中英混排 字符串中前n 个字符串     gbk 中文2位，英文1位 编码*/
    public static String cutString(String target, int num) {
        StringBuffer sb = new StringBuffer();
        int childLength = 0;
        int targetLength = target.length();
        try {
            for (int i = 0; i < targetLength; i++) {
                String tem = target.substring(i, i + 1);
                childLength += (tem.getBytes("gbk")).length;
                if (childLength <= num) {
                    sb.append(tem);
                } else {
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static SpannableStringBuilder getEmotionContent(final Context context, final TextView tv,
                                                           SpannableStringBuilder source) {
        SpannableStringBuilder spannableString = source;
        Resources res = context.getResources();

        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = null;//
            if (imgRes != null) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                if (bitmap == null) continue;
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }

    public static String getTopActivityName(Context context) {
        String topActivityName = null;
        ActivityManager activityManager =
                (ActivityManager) (context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            String topActivityClassName = f.getClassName();
            String temp[] = topActivityClassName.split("\\.");
            topActivityName = temp[temp.length - 1];
            System.out.println("topActivityName=" + topActivityName);
        }
        return topActivityName;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 格式化字符串，每隔一定位数加空格
     *
     * @param text 需要格式化的字符串
     * @param num  每隔多少位
     * @return
     */
    public static String formatBankNum(String text, int num) {
        text = " " + text;
        String result = "";
        for (int i = 0; i < text.length(); i++) {

            if (i % num == 0 && i != 0) {
                result += text.charAt(i) + "\u0020" + "\u0020";
            } else {
                result += text.charAt(i);
            }
        }
        return result.trim();
    }


    /***
     * 替换所有空格
     * @param text
     * @return
     */
    public static String replaceSpace(String text) {
        return text.replaceAll(" +", "");
    }

    /***
     * 格式化手机号码
     * @param text
     * @return
     */
    public static String formatPhoneNum(String text) {
        if (text.length() == 11) {
            String num1 = text.substring(0, 3);
            String num2 = text.substring(7, text.length());
            return num1 + "****" + num2;
        } else {
            return text;
        }

    }

    /**
     * 关键字高亮显示
     *
     * @param keyword 需要高亮的关键字
     * @param content 需要显示的文字
     * @return spannable 处理完后的结果，记得不要toString()，否则没有效果
     */
    public static SpannableString setKeyWordColor(String content, String keyword, int color) {
        try {
            SpannableString style = new SpannableString(content);
            if (isNotEmpty(keyword)) {
                int fstart = content.indexOf(keyword);
                int fend = fstart + keyword.length();
                if (fstart != -1 && fend <= content.length()) {
                    style.setSpan(new ForegroundColorSpan(color), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
            return style;
        } catch (Exception e) {
            return new SpannableString(content);
        }
    }

    /**
     * 取括号中的字符
     *
     * @param msg
     * @return
     */
    public static String extractMessageByRegular(String msg) {
        int startIndex = 0;
        int endIndex = 0;
        if (msg.contains("(") || msg.contains("（") || msg.contains(")") || msg.contains("）")) {
            if (msg.contains("(")) {
                startIndex = msg.indexOf("(");
            }
            if (msg.contains("（")) {
                startIndex = msg.indexOf("（");
            }

            if (msg.contains(")")) {
                endIndex = msg.indexOf(")");
            }
            if (msg.contains("）")) {
                endIndex = msg.indexOf("）");
            }
            return msg.substring(startIndex + 1, endIndex);
        }
        return msg;
    }

    /**
     * 是否是数字
     *
     * @param str
     * @return
     */
    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }
    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
