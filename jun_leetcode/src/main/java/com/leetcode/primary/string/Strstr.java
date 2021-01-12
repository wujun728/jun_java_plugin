package com.leetcode.primary.string;

/**
 * 实现strstr
 *
 * @author BaoZhou
 * @date 2018/12/10
 */
public class Strstr {

    public static void main(String[] args) {
        //String s = "hello", t = "ll";
        //String s = "aaaaa", t = "aaa";
        //String s = "mississippi", t = "issipi";
        String s = "ippi", t = "pi";
        System.out.println(strStr(s, t));
    }

    public static int strStr(String haystack, String needle) {
        if (needle.isEmpty() || haystack.equals(needle)) {
            return 0;
        }
        if (needle.length() > haystack.length()) {
            return -1;
        }
        int m = haystack.length();
        int n = needle.length();
        for (int i = 0; i <=  m - n; i++) {
            int j = 0;
            for (; j < n; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
            }
            if (j == n) {
                return i;
            }
        }
        return -1;

    }
}
