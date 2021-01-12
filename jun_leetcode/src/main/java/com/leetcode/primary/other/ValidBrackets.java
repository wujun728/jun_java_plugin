package com.leetcode.primary.other;


import java.util.Stack;

/**
 * 有效的括号
 *
 * @author BaoZhou
 * @date 2018/12/21
 */
public class ValidBrackets {

    public static void main(String[] args) {
        System.out.println(isValid("([)]"));
        System.out.println(isValid("(([]){})"));
        System.out.println(isValid("[({(())}[()])]"));
    }

    public static boolean isValid(String s) {
        if (s.length() == 0) {
            return true;
        }
        if (s.length() % 2 == 1) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        for (int i = s.length() - 1; i >= 0; i--) {
            if (stack.size() > 0) {
                Integer peek = stack.peek();
                if (isPair((int) s.charAt(i), peek)) {
                    stack.pop();
                } else {
                    stack.push((int) s.charAt(i));
                }
            } else {
                stack.push((int) s.charAt(i));
            }
        }
        return stack.empty();
    }

    public static boolean isPair(Integer c, Integer d) {
        if ((c == '(' && d == ')') || (c == '[' && d == ']') || (c == '{' && d == '}')) {
            return true;
        } else {
            return false;
        }
    }


}

