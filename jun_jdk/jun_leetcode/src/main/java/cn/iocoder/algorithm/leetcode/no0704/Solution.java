package cn.iocoder.algorithm.leetcode.no0704;

/**
 * https://leetcode-cn.com/problems/binary-search/
 */
public class Solution {

    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if (nums[middle] == target) {
                return middle;
            }
            if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = left + 1;
            }
        }

        return -1;
    }

}
