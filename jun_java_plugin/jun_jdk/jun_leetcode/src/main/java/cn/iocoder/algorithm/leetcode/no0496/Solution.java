package cn.iocoder.algorithm.leetcode.no0496;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/next-greater-element-i/
 */
public class Solution {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> mappings = new HashMap<>(); // key：数字，value：在 num2 中，下一个比它大的值。
        Stack<Integer> stack = new Stack<>();
        // 倒序 num2 数组，计算每个元素比它大的下一个元素
        for (int i = nums2.length - 1; i >= 0; i--) {
            int num = nums2[i];
            // 移除 stack 中，比 num 小的值
            while (!stack.isEmpty() && stack.peek() <= num) {
                stack.pop();
            }

            // 添加下一个最大值到 mappings 中
            if (!stack.isEmpty()) {
                mappings.put(num, stack.peek());
            }

            // 添加到栈中
            stack.add(num);
        }

        // 获得 num 数组中，每一个大的元素
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = mappings.getOrDefault(nums1[i], -1);
        }
        return nums1;
    }

}
