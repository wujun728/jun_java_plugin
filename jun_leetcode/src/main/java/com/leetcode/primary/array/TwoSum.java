package com.leetcode.primary.array;

/**
 * 两数之和
 *
 * @author: BaoZhou
 * @date : 2018/12/10 9:52
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = {4, 1, 2, 1, 2};

        int[] ints = twoSum(nums, 5);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                    if(nums[i] + nums[j]==target){
                        result[0] = i;
                        result[1] = j;
                        return result;
                    }
            }
        }
        return result;
    }
}

