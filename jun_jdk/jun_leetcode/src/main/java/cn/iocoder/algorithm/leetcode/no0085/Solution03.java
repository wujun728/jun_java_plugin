package cn.iocoder.algorithm.leetcode.no0085;

import java.util.Stack;

/**
 * 每一行求高度后，其实是 {@link cn.iocoder.algorithm.leetcode.no0084.Solution02} 的变种。
 */
public class Solution03 {

    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int n = matrix.length;
        int m = matrix[0].length;

        int[] heights = new int[m + 1];
        heights[m] = -1; // 此处是为了下面简化编码
        int max = 0;

        for (int i = 0; i < n; i++) {
            // 求每一层的高度
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '1') {
                    heights[j]++;
                } else {
                    heights[j] = 0;
                }
            }
            // 计算每一层的最大面积
            Stack<Integer> stack = new Stack<>();
            stack.push(-1);
            for (int j = 0; j < heights.length; j++) {
                while (stack.peek() != -1 && heights[j] < heights[stack.peek()]) {
                    max = Math.max(max, heights[stack.pop()] * (j - stack.peek() - 1));
                }
                stack.push(j);
            }
        }
        return max;
    }

}
