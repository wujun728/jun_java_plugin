package cn.iocoder.algorithm.leetcode.no0191;

/**
 * https://leetcode-cn.com/problems/number-of-1-bits/
 *
 * 位操作
 *
 * n & (n - 1) 操作
 *
 * 1 ：1 & 0 = 0
 * 2 ：10 & 01 = 0
 * 3 ：11 & 10 = 10
 */
public class Solution {

    public int hammingWeight(int n) {
        int weight = 0;
        while (n != 0) {
            // 清除最低的 1 。
            n = n & (n - 1);
            // 增加
            weight++;
        }
        return weight;
    }

}
