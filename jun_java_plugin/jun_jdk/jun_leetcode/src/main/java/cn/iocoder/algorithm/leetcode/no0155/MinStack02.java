package cn.iocoder.algorithm.leetcode.no0155;

import java.util.Stack;

/**
 * 在 {@link MinStack} 上，进行优化
 *
 * 新增一个最小值栈，不断添加元素时，记录那一刻的最小值到栈中。
 */
public class MinStack02 {

    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();

    private int min = Integer.MAX_VALUE;

    /** initialize your data structure here. */
    public MinStack02() {
    }

    public void push(int x) {
        stack.add(x);

        // 添加到最小栈
        min = Math.min(x, min);
        minStack.add(min);
    }

    public void pop() {
        stack.pop();
        minStack.pop();

        min = stack.isEmpty() ? Integer.MAX_VALUE : minStack.peek();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }

    public static void main(String[] args) {
        MinStack02 minStack = new MinStack02();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.getMin(); // --> 返回 -3.
        minStack.pop();
        minStack.top(); // --> 返回 0.
        minStack.getMin(); // --> 返回 -2.
    }

}
