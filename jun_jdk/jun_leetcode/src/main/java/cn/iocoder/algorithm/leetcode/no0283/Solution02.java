package cn.iocoder.algorithm.leetcode.no0283;

import java.util.Arrays;

/**
 * 相比 {@link Solution} 来说，减小元素的移动次数
 *
 * 核心逻辑是，不断将非 0 的值，换到前面。
 *
 * 具体的优势，胖友可以画下 {0, 1, 0, 3, 12} 的执行过程。
 *
 * 当然，如果觉得绕，理解 {@link Solution} 也是足够的
 */
public class Solution02 {

    public void moveZeroes(int[] nums) {
        int length = nums.length;
        int lastNotZeroIndex = 0; // 不断向下指向。当碰到 0 后，停留，等待 i 跳到非 0 元素，进行交换。
        for (int i = 0; i < length; i++) {
            // 如果为 0 ，则直接跳过
            if (nums[i] == 0) {
                continue;
            }
            // 交换元素
            swap(nums, lastNotZeroIndex, i);
            // 指向下一个
            lastNotZeroIndex++;
        }
    }

    private void swap(int[] nums, int i, int j) {
        if (i == j || nums[i] == nums[j]) {
            return;
        }
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        if (true) {
            int[] nums = new int[]{0, 1, 0, 3, 12};
            solution.moveZeroes(nums);
            System.out.println(Arrays.toString(nums));
        }
        if (false) {
            int[] nums = new int[]{};
            solution.moveZeroes(nums);
            System.out.println(Arrays.toString(nums));
        }
        if (false) {
            int[] nums = new int[]{1, 0};
            solution.moveZeroes(nums);
            System.out.println(Arrays.toString(nums));
        }
    }

}
