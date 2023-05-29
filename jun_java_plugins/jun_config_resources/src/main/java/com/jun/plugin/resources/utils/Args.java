package com.jun.plugin.resources.utils;

/**
 * Created by Hong on 2017/12/27.
 */
public final class Args {

    private Args() {

    }

    public static String valueTrim(String var1) {
        return var1 == null ? "" : var1.trim();
    }

    public static String value(String var1) {
        return var1 == null ? "" : var1;
    }

    public static String value(String var1, String var2) {
        return var1 == null ? var2 : var1;
    }

    public static Boolean value(Boolean var1) {
        return var1 == null ? Boolean.FALSE : var1;
    }

    public static Boolean value(Boolean var1, Boolean var2) {
        return var1 == null ? var2 : var1;
    }
}
