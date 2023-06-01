package com.lzb.onlinejava.complier.util;

public class EqualUtil {

    public static Boolean intArray(int[] a, int[] b) {
        int i = 0, j = 0;
        for (; i < a.length && j < b.length; i++, j++) {
            if (a[i] != b[j]) {
                return false;
            }
        }
        return true;
    }
}
