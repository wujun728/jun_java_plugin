package com.leetcode.primary.other;

/**
 * 颠倒二进制位
 *
 * @author BaoZhou
 * @date 2018/12/21
 */
public class ReverseBits {
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(reverseBits(43261596)));
    }

    // you need to treat n as an unsigned value
    public static int reverseBits(int n) {
        int result = 0;
        int bit = Integer.SIZE-1;
        while ( bit>=0) {
            result += (n & 1) << bit;
            bit--;
            n = n >> 1;
        }
        return result;
    }
}
