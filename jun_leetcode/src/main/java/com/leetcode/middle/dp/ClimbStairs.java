package com.leetcode.middle.dp;

/**
 * 跳跃游戏
 *
 * @author: BaoZhou
 * @date : 2018/12/21 13:17
 */
class ClimbStairs {
    public static void main(String[] args) {

        int[] nums = {3, 2, 1, 0, 4};
        int[] nums1 = {2, 3, 1, 1, 4};
        System.out.println(canJump(nums1));
    }

    public static boolean canJump(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (max >= i) {
                max = Math.max(i + nums[i], max);
            } else {
                break;
            }
        }
        return max >= nums.length - 1;
    }
}