package com.leetcode.primary.array;

/**
 * 只出现一次的数字
 *
 * @author: BaoZhou
 * @date : 2018/12/10 9:52
 */
public class SingleNumber {

    public static void main(String[] args) {
        int[] nums = {4,1,2,1,2};
        System.out.println(singleNumber(nums));
    }

    public static int singleNumber(int[] nums) {
        if(nums.length == 0) {
            return -1;
        }
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            result = result^nums[i];
        }
        return result;
    }
}

