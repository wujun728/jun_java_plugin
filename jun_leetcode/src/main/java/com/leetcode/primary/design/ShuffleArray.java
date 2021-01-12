package com.leetcode.primary.design;

import java.util.Arrays;

/**
 * Shuffle an Array
 *
 * @author BaoZhou
 * @date 2018/12/18
 */
public class ShuffleArray {

    public static void main(String[] args) {
        // 以数字集合 1, 2 和 3 初始化数组。
        int[] nums = {1, 2, 3};
        Solution solution = new Solution(nums);

        // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。
        System.out.println(Arrays.toString(solution.shuffle()));

        // 重设数组到它的初始状态[1,2,3]。
        System.out.println(Arrays.toString(solution.reset()));

        // 随机返回数组[1,2,3]打乱后的结果。
        System.out.println(Arrays.toString(solution.shuffle()));


        MinStack minStack = new MinStack();
        minStack.push(2147483646);
        minStack.push(2147483646);
        minStack.push(2147483647);
        minStack.push(2147483647);
        minStack.push(-2147483648);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }
}
