package cn.iocoder.algorithm.leetcode.no0394;

import java.util.Stack;

/**
 * 基于栈实现
 */
public class Solution02 {

    public String decodeString(String s) {
        int number = 0; // 数字
        Stack<Integer> numberStack = new Stack<>(); // 数字栈
        Stack<String> subStrStack = new Stack<>(); // 子串栈
        subStrStack.push(""); // 最终结果
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            // 数字的情况
            if (ch >= '0' && ch <= '9') {
                if (number == 0) { // 考虑 3[a2[c]] 的情况
                    subStrStack.push("");
                }
                // 拼接数字
                number = number * 10 + (ch - '0');
                continue;
            }
            // 左括号的情况
            if (ch == '[') {
                numberStack.push(number);
                number = 0;
                continue;
            }
            // 右括号的情况
            if (ch == ']') {
                // 生成新的子串
                String text = gen(subStrStack.pop(), numberStack.pop());
                // 根据情况，设置 subStr
                if (!subStrStack.isEmpty()) {
                    subStrStack.push(subStrStack.pop() + text);
                } else {
                    subStrStack.push(text);
                }
                continue;
            }
            // 字母的情况
            subStrStack.push(subStrStack.pop() + ch);
        }

        return subStrStack.pop();
    }

    private String gen(String str, int number) {
        if (number == 1) {
            return str;
        }
        if ((number & 1) == 1) {
            return str + gen(str + str, number / 2);
        } else {
            return gen(str + str, number / 2);
        }
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.decodeString("2[ab]"));
//        System.out.println(solution.decodeString("2[2[ab]]"));
//        System.out.println(solution.decodeString("2[2[ab]]bc"));
        System.out.println(solution.decodeString("3[a2[c]]"));
//        System.out.println(solution.decodeString("2[abc]3[cd]ef"));
//        System.out.println(solution.decodeString("2[2[y]pq4[2[jk]e1[f]]]"));
        System.out.println(solution.decodeString("3[z]2[2[y]pq4[2[jk]e1[f]]]ef"));
//        "yy pq jkjkefpqjkjkefpqjkjkefpqjkjkef"
//        "yy pq jkjkefjkjkefjkjkefjkjk yypqjkjkefjkjkefjkjkefjkjk"
    }

}
