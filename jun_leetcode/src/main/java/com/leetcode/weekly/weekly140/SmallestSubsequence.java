package com.leetcode.weekly.weekly140;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 不同字符的最小子序列
 *
 * @author BaoZhou
 * @date 2019/6/9 15:05
 */
public class SmallestSubsequence {


    @Test
    public void test() {
        String test = "ecbacba";
        System.out.println(smallestSubsequence(test));
    }



    public String smallestSubsequence(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            map.put(cur, map.getOrDefault(cur, 0) + 1);
        }
        //map表示某一个字母出现的次数
        Set<Character> set = new HashSet<>();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            map.put(cur, map.get(cur) - 1);
            if (set.contains(cur)) {
                continue;
            }
            while (!stack.isEmpty() && cur < stack.peek() && map.get(stack.peek()) > 0) {
                set.remove(stack.pop());
            }
            set.add(cur);
            stack.push(cur);
        }
        StringBuilder sb = new StringBuilder();
        for (char ch : stack) {
            sb.append(ch);
        }
        return sb.toString();
    }
}

