package com.jun.plugin.resources.utils;

/**
 * Created By Hong on 2018/8/6
 **/
public final class StringUtils {

    public static boolean isEmpty(String var1) {
        return var1 == null || var1.equals("") ? true : false;
    }

    public static boolean isNotEmpty(String var1) {
        return !isEmpty(var1);
    }
}
