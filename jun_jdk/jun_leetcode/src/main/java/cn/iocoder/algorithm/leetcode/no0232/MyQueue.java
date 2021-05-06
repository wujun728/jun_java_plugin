package cn.iocoder.algorithm.leetcode.no0232;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/implement-queue-using-stacks/
 *
 * 通过两个栈，模拟队列
 */
public class MyQueue {

    private Stack<Integer> stackA = new Stack<>();
    private Stack<Integer> stackB = new Stack<>();

    /** Initialize your data structure here. */
    public MyQueue() {

    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        stackA.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        assert !empty();

        move();

        return stackB.pop();
    }

    /** Get the front element. */
    public int peek() {
        assert !empty();

        move();

        return stackB.peek();
    }

    /**
     * 将 stackA 搬运到 stackB 中，当 stackA 非空的情况下。
     */
    private void move() {
        // 当 stackA 非空，不搬运到其中
        if (!stackB.empty()) {
            return;
        }

        // 搬运
        while (!stackA.empty()) {
            stackB.push(stackA.pop());
        }
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return stackA.empty() && stackB.empty();
    }

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(2);
        queue.peek();  // 返回 1
        queue.pop();   // 返回 1
        queue.empty(); // 返回 false
    }

}
