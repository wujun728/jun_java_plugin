package cn.iocoder.algorithm.leetcode.no0645;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/set-mismatch/
 *
 * 通过数值范围为 [2, 10000] ，实现交换排序，这样空间复杂度为 O(1)
 * 时间复杂度是 O(n)
 */
public class Solution {

    public int[] findErrorNums(int[] nums) {
        // 通过交换位置，进行排序
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1 // 当前位置，不等与 i + 1 自己
                && nums[nums[i] - 1] != nums[i]) { // 交换位置，不等于当前位置的值
                swap(nums, i, nums[i] - 1);
            }
        }

        // 计算重复值
        for (int i = 0; i < nums.length; i++) {
            // 符合当前位置，直接跳过
            if (nums[i] == i + 1) {
                continue;
            }
            return new int[]{nums[i], i + 1};
        }

        // 不符合条件
        return null;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        if (false) {
            int[] result = solution.findErrorNums(new int[]{4, 1, 2, 2});
            System.out.println(Arrays.toString(result));
        }
        if (false) {
            int[] result = solution.findErrorNums(new int[]{1, 2, 2, 4});
            System.out.println(Arrays.toString(result));
        }
        if (true) {
            int[] result = solution.findErrorNums(new int[]{2, 3, 2});
            System.out.println(Arrays.toString(result));
        }
    }

}
