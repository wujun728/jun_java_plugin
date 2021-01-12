package com.leetcode.primary.design;

import java.util.Stack;

/**
 * 最小栈
 *
 * @author: BaoZhou
 * @date : 2018/12/18 16:59
 */
class MinStack {
    private Stack<Integer> src = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();
    private int min;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        min = Integer.MAX_VALUE;
    }

    public void push(int x) {
        src.push(x);
        if (x <= min) {
            min = x;
        }
        minStack.push(min);
    }

    public void pop() {
        minStack.pop();
        if (src.size() == 1) {
            min = Integer.MAX_VALUE;
        }
        else if (src.pop() == min) {
            min = minStack.peek();
        }

    }

    public int top() {
        return src.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
