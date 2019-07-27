package com.android.baselibrary.util;


import android.text.TextUtils;
import android.util.Log;

/**
 * 
 * @author byl
 *
 */
public class EmojiUtil {
 
    /**
     * �ж��Ƿ���emoji
     * @param str
     * @return 
     */
    public static boolean containsEmoji(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        
        for (int i = 0; i < str.length(); i++) {
            char codePoint = str.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                return true;
            }
        }
        return false;
    }
 
    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }
 
    /**
     * ����emoji ���� ������������͵��ַ�
     * @param
     * @return
     */
    public static String filterEmoji(String str) {
 
        if (!containsEmoji(str)) {
            return str;//����ֱ�ӷ���
        }else{
        	Log.e("jj", "�ַ��к���emoji����");
        }
        //���������
        StringBuilder buf = null;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char codePoint = str.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(str.length());
                }
                buf.append(codePoint);
            } else {
            	
            }
        }
 
        if (buf == null) {
            return "";//���ȫ��Ϊ emoji���飬�򷵻ؿ��ַ�
        } else {
            return buf.toString();
        }
 
    }
}