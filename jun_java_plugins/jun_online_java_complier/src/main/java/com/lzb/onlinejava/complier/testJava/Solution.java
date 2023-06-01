package com.lzb.onlinejava.complier.testJava;

/**
 * @author: bin
 * @email: 958615915@qq.com
 * @create: 2019-08-03
 */
public class Solution {
    public String sayHello() {
        return "hello word";
    }

    public String sayHello(String[] strs) {
        String s = "";
        for (int i = 0; i < strs.length; i++) {
            s = s + strs[i];
        }
        return s;
    }

    public String sayHello(int i, int j) {
        return "i + j =" + (i + j);
    }

    public Boolean isTrue() {
        return true;
    }

    public int[] sayIntArray() {
        return new int[]{1, 3, 43};
    }

}
