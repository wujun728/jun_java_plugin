package com.leetcode.primary.math;

/**
 * 3的幂
 *
 * @author BaoZhou
 * @date 2018/12/18
 */
public class PowerOfThree {
    public static void main(String[] args) {
        System.out.println(isPowerOfThree(10));
    }



    public static boolean isPowerOfThree(int n) {
        if(n==0) return false;
        double v = Math.log10(n) / Math.log10(3);
        return  v == Math.ceil(v);
    }

}
