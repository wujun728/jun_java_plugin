package com.leetcode.primary.dp;

/**
 * 最大子序和
 *
 * @author: BaoZhou
 * @date : 2018/12/17 22:05
 */
class MaxSubArray {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(nums));
    }

    public static int maxSubArray(int[] nums) {
        int max = nums[0];
        int n = nums[0];
        for (int i = 1; i < nums.length; i++) {
            n = Math.max(n + nums[i], nums[i]);
            if (n > max) {
                max = n;
            }
        }
        return max;
    }
}