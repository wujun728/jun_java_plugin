package com.leetcode.primary.other;

/**
 * 汉明距离
 *
 * @author BaoZhou
 * @date 2018/12/21
 */
public class HammingDistance {
    public static void main(String[] args) {
        System.out.println(hammingDistance(1, 4));
    }

    // you need to treat n as an unsigned value
    public static int hammingDistance(int x, int y) {
        int count = 0;
        for (int i = 0; i < Integer.SIZE; i++) {
            if (((x >> i & 1) ^ (y >> i & 1)) == 1) {
                count++;
            }
        }
        return count;
    }
}
