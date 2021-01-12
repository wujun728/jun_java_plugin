package com.leetcode.weekly.weekly137;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 删除字符串中的所有相邻重复项
 *
 * @author: BaoZhou
 * @date : 2019/5/19 11:21
 */
public class RemoveDuplicates {
    @Test
    public void test() {
        String s = "";
        System.out.println(removeDuplicates(s));
    }

    public String removeDuplicates(String s) {
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < chars.length; i++) {
            if (stack.isEmpty()) {
                stack.push(chars[i]);
                continue;
            }
            Character peek = stack.peek();
            if (peek.equals(chars[i])) {
                stack.pop();
            } else {
                stack.push(chars[i]);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.isEmpty()) {
            stringBuilder.append(stack.pop());
        }
        return stringBuilder.reverse().toString();

    }
}
