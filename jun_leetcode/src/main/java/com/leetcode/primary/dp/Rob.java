package com.leetcode.primary.dp;

/**
 * 打家劫舍
 *
 * @author: BaoZhou
 * @date : 2018/12/17 22:05
 */
class Rob {
    public static void main(String[] args) {
        int[] nums = {2, 1, 1, 2};
        System.out.println(rob(nums));
    }

    public static int rob(int[] nums) {
        int max = 0;
        int n1 = 0;
        int n2 = 0;
        if (nums.length == 0) {
            return 0;
        }
        for (int i = 0; i < nums.length;  i++) {
            int temp = n1;
            n1 = Math.max(n1, n2 + nums[i]);
            n2 = temp;
            if (n1 > max) {
                max = n1;
            }
        }
        return max;
    }
}