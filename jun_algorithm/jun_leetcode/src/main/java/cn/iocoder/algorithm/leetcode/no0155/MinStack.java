package cn.iocoder.algorithm.leetcode.no0155;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/min-stack/
 *
 * 暴力
 */
public class MinStack {

    private List<Integer> stack = new ArrayList<>();

    private int min = Integer.MAX_VALUE;

    /** initialize your data structure here. */
    public MinStack() {

    }

    public void push(int x) {
        stack.add(x);

        if (x < min) {
            min = x;
        }
    }

    public void pop() {
        int top = stack.remove(stack.size() - 1);
        if (top != min) {
            return;
        }

        // 重新求最小值
        min = Integer.MAX_VALUE;
        for (int x : stack) {
            if (x < min) {
                min = x;
            }
        }
    }

    public int top() {
        return stack.get(stack.size() - 1);
    }

    public int getMin() {
        return min;
    }

}
