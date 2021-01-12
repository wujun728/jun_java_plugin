package com.leetcode.middle.dp;

import java.util.Arrays;

/**
 * Longest Increasing Subsequence
 *
 * @author: BaoZhou
 * @date : 2018/12/21 13:17
 */
class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        int[] nums1 = {1,3,6,7,9,4,10,5,6};
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(lengthOfLIS(nums1));
    }

    public static int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int dpResult[] = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dpResult[i] = Math.max(dpResult[i], dpResult[j]);
                }
            }
            dpResult[i]++;
        }
        System.out.println(Arrays.toString(dpResult));
        int result = 0;
        for (int i = 0; i < dpResult.length; i++) {
            result = Math.max(dpResult[i],result);
        }
        return result;
    }
}