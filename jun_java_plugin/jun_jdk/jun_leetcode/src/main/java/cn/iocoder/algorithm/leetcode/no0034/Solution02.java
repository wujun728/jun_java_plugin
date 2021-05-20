package cn.iocoder.algorithm.leetcode.no0034;

import java.util.Arrays;

/**
 * 两次变种的二分查找
 */
public class Solution02 {

    public int[] searchRange(int[] nums, int target) {
        // 寻找左边
        int left = this.searchRangeLeftOrderRight(nums, target, true);

        // 未找到
        if (left == nums.length || nums[left] != target) {
            return new int[]{-1, -1};
        }

        // 寻找右边
        int right = this.searchRangeLeftOrderRight(nums, target, false);
        return new int[]{left, right};
    }

    public int searchRangeLeftOrderRight(int[] nums, int target, boolean left) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int middle = low + ((high - low) >> 1);
            if (nums[middle] == target) {
                if (left) {
                    high = middle - 1;
                } else {
                    low = middle + 1;
                }
            } else if (nums[middle] > target) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }

        return left ? low : high;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(Arrays.toString(solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
        System.out.println(Arrays.toString(solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6)));
    }

}
