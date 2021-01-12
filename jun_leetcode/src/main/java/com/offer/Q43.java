package com.offer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;


/**
 * 左旋转字符串
 *
 * @author BaoZhou
 * @date 2020-6-21
 */

public class Q43 {
    @Test
    public void result() {
        String data ="abcdefg";
        System.out.println(LeftRotateString(data, 2));
    }

    public String LeftRotateString(String str,int n) {
        if (str == null || n > str.length()) {
            return str;
        }
        return str.substring(n) + str.substring(0, n);

    }
}



