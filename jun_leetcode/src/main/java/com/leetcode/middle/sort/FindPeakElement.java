package com.leetcode.middle.sort;

import org.junit.jupiter.api.Test;

/**
 * 寻找峰值
 * @author BaoZhou
 * @date 2019/1/22
 */
public class FindPeakElement {
    @Test
    void test() {
        int[] nums = {2, 3, 4, 3, 2, 1};
        System.out.println(findPeakElement(nums));
    }

    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int mid = right / 2;
        while (left < right) {
            if (mid == left) return nums[right] > nums[left] ? right : left;
            if (nums[mid] < nums[mid + 1]) {
                left = mid;
            } else if (nums[mid] > nums[mid + 1]) {
                right = mid;
            }
            mid = left + (right - left) / 2;
        }
        return 0;
    }


}
