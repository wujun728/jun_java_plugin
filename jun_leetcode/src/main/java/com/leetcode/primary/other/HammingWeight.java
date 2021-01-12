package com.leetcode.primary.other;

/**
 * 汉明距离
 * @author BaoZhou
 * @date 2018/12/21
 */
public class HammingWeight {
    public static void main(String[] args) {
        System.out.println(hammingWeight(-3));
    }

    // you need to treat n as an unsigned value
    public static int hammingWeight(int n) {
        int count = 0;
        for (int i = 0; i < Integer.SIZE; i++) {
            if (((n >> i) & 1) == 1) {
                count++;
            }
        }
        return count;
    }
}
