package com.leetcode.middle.array;

/**
 * 最长回文子串
 *
 * @author BaoZhou
 * @date 2018/12/24
 */
public class IncreasingTriplet {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 5, 4, 8, 6};
        int[] nums1 = new int[]{5, 4, 3, 2, 1};
        int[] nums2 = new int[]{9, 13, 1, 11, 2, 0, 19};
        System.out.println(increasingTriplet(nums));
        System.out.println(increasingTriplet(nums1));
        System.out.println(increasingTriplet(nums2));
    }

    public static boolean increasingTriplet(int[] nums) {
        if (nums.length < 3) {
            return false;
        }
        int a = Integer.MAX_VALUE, b = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= a) {
                a = nums[i];
                continue;
            }
            if (nums[i] <= b) {
                b = nums[i];
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

}
