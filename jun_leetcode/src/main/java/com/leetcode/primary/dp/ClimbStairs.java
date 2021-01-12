package com.leetcode.primary.dp;

/**
 * 爬楼梯
 *
 * @author: BaoZhou
 * @date : 2018/12/17 22:05
 */
class ClimbStairs {
    public static void main(String[] args) {
        System.out.println(climbStairs(3));
    }

    public static int climbStairs(int n) {
        int a = 1, b = 1;
        while (n!= 0) {
            b += a;
            a = b - a;
            n --;
        }
        return a;
    }
}