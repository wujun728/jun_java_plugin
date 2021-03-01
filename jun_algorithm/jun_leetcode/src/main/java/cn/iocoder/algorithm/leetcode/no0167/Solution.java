package cn.iocoder.algorithm.leetcode.no0167;

/**
 * https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted
 */
public class Solution {

    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            }
            if (sum > target) {
                right--;
            } else {
                left++;
            }
        }

        return new int[]{-1, -1};
    }

}
