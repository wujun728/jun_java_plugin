package com.itmuch.cloud.common.utils;

import java.util.regex.Pattern;

public class RegUtils {
	 public static final String  REG_MOBILE_TELEPHONE    = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|4|5|6|7|8|9])\\d{8}$";
	 public static final String  REG_EAMIL = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	 /**
     * 判断是否是符合的移动电话
     *
     * @param phone 判断的字符串
     * @return 是否是符合的移动电话
     */
    public static boolean isTelePhone(String phone) {
        if (phone.length()==0) {
            return false;
        }
        Pattern pattern = Pattern.compile(REG_MOBILE_TELEPHONE);
        return pattern.matcher(phone).matches();
    }
    /**
     * 判断是否是符合的邮箱
     *
     * @param phone 判断的字符串
     * @return 是否是符合的邮箱
     */
    public static boolean isEail(String eamil) {
        if (eamil.length()==0) {
            return false;
        }
        Pattern pattern = Pattern.compile(REG_EAMIL);
        return pattern.matcher(eamil).matches();
    }

}
