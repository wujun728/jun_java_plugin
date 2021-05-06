package cn.iocoder.algorithm.leetcode.no0084;

import java.util.Stack;

/**
 * 在 {@link Solution} 的基础上优化。
 *
 * 通过栈，避免重复遍历。
 *
 * 顺序遍历节点：
 *
 * 【出栈】如果，当前节点 < 栈里最后一个节点 A ，说明 A 节点已经无法向右走。直接 pop 计算面积。
 * 然后，入栈。这样，当前当前节点，可以看看自己是否可以往右边走。
 */
public class Solution02 {

    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // 方便计算。
        int max = 0;

        // 顺序遍历
        for (int i = 0; i < heights.length; i++) {
            while (stack.peek() != -1 && heights[i] < heights[stack.peek()]) {
                int last = stack.pop(); // 栈里最后一个节点。注意，此处要出栈
                int lastLast = stack.peek(); // 栈里倒数第二个节点。
                int height = heights[last];
                int width = i - lastLast - 1; // 可以把 lastLast 理解成左边界，i 理解成有边界。极端情况下，左边界是 -1 ，我们预先加入栈里的 -1 。
                // 求最大面基
                max = Math.max(max, height * width);
            }
            stack.push(i);
        }

        // 认为 heights.length 为最低值，也就是说 heights.length 是边界。
        while (stack.peek() != -1) {
            int last = stack.pop(); // 栈里最后一个节点。注意，此处要出栈
            int lastLast = stack.peek(); // 栈里倒数第二个节点。
            int height = heights[last];
            int width = heights.length - lastLast - 1;
            // 求最大面基
            max = Math.max(max, height * width);
        }

        return max;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
        System.out.println(solution.largestRectangleArea(new int[]{1}));
    }

}
