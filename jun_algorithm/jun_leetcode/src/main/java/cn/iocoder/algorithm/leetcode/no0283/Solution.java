package cn.iocoder.algorithm.leetcode.no0283;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/move-zeroes/description/
 */
public class Solution {

    public void moveZeroes(int[] nums) {
        int index = 0;
        for (int num : nums) {
            // 如果为 0 ，则直接跳过
            if (num == 0) {
                continue;
            }
            // 赋值到当前位置
            nums[index] = num;
            // 移动到下一个位置
            index++;
        }
        // 将跳过的 0 ，赋值到数组尾部
        Arrays.fill(nums, index, nums.length, 0);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        if (false) {
            int[] nums = new int[]{0, 1, 0, 3, 12};
            solution.moveZeroes(nums);
            System.out.println(Arrays.toString(nums));
        }
        if (false) {
            int[] nums = new int[]{};
            solution.moveZeroes(nums);
            System.out.println(Arrays.toString(nums));
        }
        if (true) {
            int[] nums = new int[]{1, 0};
            solution.moveZeroes(nums);
            System.out.println(Arrays.toString(nums));
        }
    }

}
