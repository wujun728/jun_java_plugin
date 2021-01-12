package com.leetcode.primary.string;

/**
 * 整数反转
 * @author BaoZhou
 * @date 2018/12/10
 */
public class IntString {

    public static void main(String[] args) {
        int s = -100000000;
        System.out.println(reverse(s));
    }

    public static int reverse(int x) {
        int res = 0;
        while (x != 0) {
            int temp = res * 10 + x % 10;
            //判断数字是否溢出
            if (temp / 10 != res) {
                return 0;
            }
            res = temp;
            x /= 10;
        }
        return res;
    }
}
