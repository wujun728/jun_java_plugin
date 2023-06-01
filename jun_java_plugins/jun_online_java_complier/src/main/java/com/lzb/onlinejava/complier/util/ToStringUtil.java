package com.lzb.onlinejava.complier.util;

public class ToStringUtil {

    public static String IntArrayToString(int [] array) {
        String str = "[";

        for (int i = 0; i < array.length; i++) {
            str = str + array[i] + ",";
        }
        str = str.substring(0, str.length() - 1);
        str = str + "]";
        return str;
    }

    public static void main(String[] args) {
        System.out.println(ToStringUtil.IntArrayToString(new int[]{1, 3, 4}));
    }
}
