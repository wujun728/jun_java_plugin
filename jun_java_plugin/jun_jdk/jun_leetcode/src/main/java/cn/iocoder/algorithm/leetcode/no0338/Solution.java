package cn.iocoder.algorithm.leetcode.no0338;

/**
 * https://leetcode-cn.com/problems/counting-bits/
 *
 * 位操作
 */
public class Solution {

    public int[] countBits(int num) {
        int[] bits = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            bits[i] = 1 + bits[i & (i - 1)];
        }
        return bits;
    }

}
