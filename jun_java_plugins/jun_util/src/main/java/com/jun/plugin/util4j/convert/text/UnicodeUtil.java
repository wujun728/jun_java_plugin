package com.jun.plugin.util4j.convert.text;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeUtil {

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {
	    StringBuffer string = new StringBuffer();
	    String[] hex = unicode.split("\\\\u");
	    for (int i = 1; i < hex.length; i++) {
	        int data = Integer.parseInt(hex[i], 16);
	        string.append((char) data);
	    }
	    return string.toString();
	}
	
	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {
	    StringBuffer unicode = new StringBuffer();
	    for (int i = 0; i < string.length(); i++) {
	        char c = string.charAt(i);
	        unicode.append("\\u" + Integer.toHexString(c));
	    }
	    return unicode.toString();
	}
	
	/**
	 * 根据Unicode编码完美的判断中文汉字和符号   
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {   
	    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);   
	    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS   
	    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B   
	    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS   
	    || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)  
	    {   
	        return true;   
	    }   
	    return false;   
	}   
	  
	/**
	 * 判断中文汉字和符号   
	 * @param strName
	 * @return
	 */
	public static boolean isChinese(String strName)  
	{   
	    char[] ch = strName.toCharArray();   
	    for (int i = 0; i < ch.length; i++)  
	    {   
	        char c = ch[i];   
	        if (isChinese(c)) {   
	            return true;   
	        }   
	    }   
	    return false;   
	} 
	
	/**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
 
    }
	
	public static void main(String[] args) {
		String s="\\u6700\\u4ee3\\u7801\\u7f51\\u7ad9\\u5730\\u5740\\u3a\\u77\\u77\\u77\\u2e\\u7a\\u75\\u69\\u64\\u61\\u69\\u6d\\u61\\u2e\\u63\\u6f\\u6d";
		String[] hex = s.split("\\\\u");
		System.out.println(Arrays.toString(hex));
	}
}
