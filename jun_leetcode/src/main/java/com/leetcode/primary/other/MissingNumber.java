package com.leetcode.primary.other;

/**
 * 缺失数字
 * @author BaoZhou
 * @date 2018/12/21
 */
public class MissingNumber {
    public static void main(String[] args) {
        int[] nums = {9, 6, 4, 2, 3, 5, 7, 0, 1};
        System.out.println(missingNumber(nums));

    }

    public static int missingNumber(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length + 1; i++) {
            sum = sum + i;
            if (i != nums.length) {
                sum = sum - nums[i];
            }
        }
        return sum;
    }
}
