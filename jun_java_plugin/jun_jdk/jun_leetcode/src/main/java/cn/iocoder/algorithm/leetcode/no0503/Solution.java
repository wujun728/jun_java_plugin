package cn.iocoder.algorithm.leetcode.no0503;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/next-greater-element-ii/
 */
public class Solution {

    public int[] nextGreaterElements(int[] nums) {
        Map<Integer, Integer> mappings = new HashMap<>(); // key：数字的下标，value：在 num 中，下一个比它大的值。
        Stack<Integer> stack = new Stack<>(); // 数字的下标
        Queue<Integer> queue = new LinkedList<>(); // 未处理（获得比它大的值）的数字的下标
        // 倒序 nums 数组，计算每个元素比它大的下一个元素
        for (int i = nums.length - 1; i >= 0; i--) {
            int num = nums[i];
            // 移除 stack 中，比 num 小的值
            while (!stack.isEmpty() && nums[stack.peek()] <= num) {
                stack.pop();
            }

            // 添加下一个最大值到 mappings 中
            if (!stack.isEmpty()) {
                mappings.put(i, nums[stack.peek()]);
            } else { // 添加到栈里
                queue.add(i);
            }

            // 添加到栈中
            stack.add(i);
        }

        // 正序 nums 数组，处理 queue
        if (!queue.isEmpty()) {
            int index = 0;
            while (!queue.isEmpty()) { // 从队头，处理每一个
                for (; index < nums.length; index++) {
                    if (nums[index] > nums[queue.peek()]) {
                        mappings.put(queue.peek(), nums[index]);
                        break;
                    }
                }
                queue.remove();
            }
        }

        // 获得 num 数组中，每一个大的元素
        for (int i = 0; i < nums.length; i++) {
            nums[i] = mappings.getOrDefault(i, -1);
        }
        return nums;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.nextGreaterElements(new int[]{5, 1, 6, 4, 3})));
        System.out.println(Arrays.toString(solution.nextGreaterElements(new int[]{1, 2, 1})));
    }

}
