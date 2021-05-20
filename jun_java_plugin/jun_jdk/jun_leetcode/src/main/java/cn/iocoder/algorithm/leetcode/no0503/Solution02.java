package cn.iocoder.algorithm.leetcode.no0503;

import java.util.*;

/**
 * 在 {@link Solution} 的基础上，优化代码，更加简洁。
 *
 * 将 num 数组，翻一倍。思路是一致的。
 */
public class Solution02 {

    public int[] nextGreaterElements(int[] nums) {
        Map<Integer, Integer> mappings = new HashMap<>(); // key：数字的下标，value：在 num 中，下一个比它大的值。
        Stack<Integer> stack = new Stack<>(); // 数字的下标
        // 倒序 nums 数组，计算每个元素比它大的下一个元素
        for (int i = nums.length * 2 - 1; i >= 0; i--) {
            // 前半段，倒序；后半段，正序
            int index;
            if (i <= nums.length - 1) {
                index = i;
            } else {
                index = i - nums.length;
            }
            int num = nums[index];

            // 移除 stack 中，比 num 小的值
            while (!stack.isEmpty() && nums[stack.peek()] <= num) {
                stack.pop();
            }

            // 添加下一个最大值到 mappings 中
            if (!stack.isEmpty()) {
                mappings.put(index, nums[stack.peek()]);
            }

            // 添加到栈中
            stack.add(index);
        }

        // 获得 num 数组中，每一个大的元素
        for (int i = 0; i < nums.length; i++) {
            nums[i] = mappings.getOrDefault(i, -1);
        }
        return nums;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(Arrays.toString(solution.nextGreaterElements(new int[]{5, 1, 6, 4, 3})));
        System.out.println(Arrays.toString(solution.nextGreaterElements(new int[]{1, 2, 1})));
    }

}
