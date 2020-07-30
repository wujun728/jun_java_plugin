package cn.iocoder.algorithm.leetcode.no0716;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/max-stack/
 *
 * 这个实现方式，{@link #popMax()} 的性能，会是 O(n) ，可能比较差。
 */
public class MaxStack {

    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> maxStack = new Stack<>();

    private int max = Integer.MIN_VALUE;

    /** initialize your data structure here. */
    public MaxStack() {
    }

    public void push(int x) {
        stack.push(x);

        max = Math.max(max, x);
        maxStack.push(max);
    }

    public int pop() {
        int pop = stack.pop();
        maxStack.pop();

        max = maxStack.isEmpty() ? Integer.MIN_VALUE : maxStack.peek();

        return pop;
    }

    public int top() {
        return stack.peek();
    }

    public int peekMax() {
        return max;
    }

    public int popMax() {
        int tmpMax = max;

        // 将不等于该值的，添加到 pops 中
        List<Integer> pops = new ArrayList<>();
        while (!stack.isEmpty() && stack.peek() != tmpMax) {
            pops.add(this.pop());
        }

        // 移除 max
        this.pop();

        // 添加元素到其中
        for (int i = pops.size() - 1; i >= 0; i--) {
            this.push(pops.get(i));
        }

        // 返回
        return tmpMax;
    }

}
