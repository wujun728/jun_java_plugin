package cn.iocoder.algorithm.leetcode.no0136;

/**
 * https://leetcode-cn.com/problems/single-number/
 *
 * 位操作
 *
 * 考核 ^ 异或运算符 https://blog.csdn.net/kybd2006/article/details/3727218
 */
public class Solution {

    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

}
