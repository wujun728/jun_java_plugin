package cn.iocoder.algorithm.leetcode.no0215;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
 *
 * 快速排序，使用内置库
 */
public class Solution {

    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);

        return nums[nums.length - k];
    }

}
