package cn.iocoder.algorithm.leetcode.no0034;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 */
public class Solution {

    public int[] searchRange(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if (nums[middle] == target) {
                return getRange(nums, target, middle);
            }
            if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        // 相比来说，返回 left ，表示最接近的位置
        return new int[]{-1, -1};
    }

    private int[] getRange(int[] nums, int target, int middle) {
        int start = middle;
        int end = middle;
        while (start > 0 && nums[start - 1] == target) {
            start--;
        }
        while (end < nums.length - 1 && nums[end + 1] == target) {
            end++;
        }
        return new int[]{start, end};
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
        System.out.println(Arrays.toString(solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6)));
    }

}
