package com.offer;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * 用两个栈实现队列
 * @author: BaoZhou
 * @date : 2020/5/28 9:56 上午
 */
public class Q5 {
    @Test
    public void result() {
        push(1);
        push(2);
        push(3);
        push(4);
        push(5);
        push(6);
        System.out.println(pop());
        System.out.println(pop());
        System.out.println(pop());
        System.out.println(pop());
        System.out.println(pop());
        System.out.println(pop());
    }

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
}
