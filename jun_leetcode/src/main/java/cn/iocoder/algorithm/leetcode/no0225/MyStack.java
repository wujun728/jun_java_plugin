package cn.iocoder.algorithm.leetcode.no0225;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/implement-stack-using-queues/
 *
 * 通过单个队列，不断弹出，添加到尾巴。
 */
public class MyStack {

    private Queue<Integer> queue = new LinkedList<>();

    /** Initialize your data structure here. */
    public MyStack() {
    }

    /** Push element x onto stack. */
    public void push(int x) {
        queue.add(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        assert !empty();

        move();

        return queue.remove();
    }

    /** Get the top element. */
    public int top() {
        assert !empty();

        int top = pop();
        queue.add(top);

        return top;
    }

    private void move() {
        int size = queue.size();
        for (int i = 1; i < size; i++) {
            queue.add(queue.remove());
        }
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue.isEmpty();
    }

}
