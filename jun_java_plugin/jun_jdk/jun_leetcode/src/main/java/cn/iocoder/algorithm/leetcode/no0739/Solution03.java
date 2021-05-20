package cn.iocoder.algorithm.leetcode.no0739;

import java.util.Arrays;
import java.util.Stack;

/**
 * 在 {@link Solution02} 的基础上，增加最大值的判断
 */
public class Solution03 {

    public int[] dailyTemperatures(int[] T) {
        Stack<Integer> stack = new Stack<>(); // 值为 index 。在该栈中，不断移除栈顶比当前元素小的值，这样，保留下来就是比当前元素大的值的序号。
        int[] results = new int[T.length];
        int max = Integer.MIN_VALUE;
        for (int i = T.length - 1; i >= 0; i--) {
            // 移除栈顶，比当前元素小的
            if (T[i] >= max) {
                stack.clear();
                max = T[i];
            } else {
                while (!stack.isEmpty() && T[stack.peek()] <= T[i]) {
                    stack.pop();
                }
            }

            // 设置结果
            results[i] = stack.isEmpty() ? 0 : stack.peek() - i;

            // 将当前元素，添加到栈中
            stack.push(i);
        }
        return results;
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
        // 1 1 4 2 1 1 0 0
        // 1 1 4 2 1 2 0 0
        System.out.println(Arrays.toString(solution.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
//        [8,1,5,4,1,2,1,1,0,0]
//        [8,1,5,4,3,2,1,1,0,0]
//        System.out.println(Arrays.toString(solution.dailyTemperatures(new int[]{89, 62, 70, 58, 47, 47, 46, 76, 100, 70})));
    }

}
