package cn.iocoder.algorithm.leetcode.no1003;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/check-if-word-is-valid-after-substitutions/
 */
public class Solution {

    public boolean isValid(String S) {
        if (S.length() % 3 > 0) {
            return false;
        }

        Stack<Character> stack = new Stack<>();
        for (Character ch : S.toCharArray()) {
            switch (ch) {
                case 'a':
                    stack.push(ch);
                    break;
                case 'b':
                    if (!stack.isEmpty() && stack.peek() != 'a') { // 提前剪枝。貌似跑了下，反倒性能更差。所以，可以去掉这条。
                        return false;
                    }
                    stack.push(ch);
                    break;
                case 'c':
                    if (stack.size() < 2) {
                        return false;
                    }
                    Character b = stack.pop();
                    Character a = stack.pop();
                    if (b != 'b' || a != 'a') {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isValid("aabcbc"));
    }

}
