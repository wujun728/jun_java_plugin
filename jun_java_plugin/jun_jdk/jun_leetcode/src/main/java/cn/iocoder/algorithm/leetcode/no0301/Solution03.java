package cn.iocoder.algorithm.leetcode.no0301;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 回溯算法，通过暴力 DFS 实现
 *
 * 参考 https://leetcode-cn.com/problems/remove-invalid-parentheses/solution/shan-chu-wu-xiao-de-gua-hao-by-leetcode/ 方法一
 */
public class Solution03 {

    private Set<String> result = new HashSet<>();
    private int delMinCount = Integer.MAX_VALUE; // 当前删除的数量

    public List<String> removeInvalidParentheses(String s) {
        this.backtracking(s, 0, 0, 0, new StringBuffer(s.length()), 0);
        return new ArrayList<>(result);
    }

    private void backtracking(String s, int index, int leftCount, int rightCount, StringBuffer current, int delCount) {
        // 遍历完字符串
        if (index == s.length()) {
            if (leftCount == rightCount) { // 说明符合条件
                if (delCount < delMinCount) { // 删除数量更少。
                    delMinCount = delCount;
                    result.clear();
                }
                if (delCount == delMinCount) {
                    result.add(current.toString());
                }
            }
            return;
        }
        int length = current.length();

        // 非左右括号
        char ch = s.charAt(index);
        if (ch != '(' && ch != ')') {
            current.append(ch);
            this.backtracking(s, index + 1, leftCount, rightCount, current, delCount);
            current.deleteCharAt(length);
            return;
        }

        // 直接忽略当前的括号，往下递归
        this.backtracking(s, index + 1, leftCount, rightCount, current, delCount + 1);

        // 如果是左括号
        if (ch == '(') {
            current.append(ch);
            this.backtracking(s, index + 1, leftCount + 1, rightCount, current, delCount);
            current.deleteCharAt(length);
            return;
        }

        // 如果是右括号，必须数量比左括号小，不然就是不合法的表达式
        if (leftCount > rightCount) {
            current.append(ch);
            this.backtracking(s, index + 1, leftCount, rightCount + 1, current, delCount);
            current.deleteCharAt(length);
        }
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
//        System.out.println(solution.removeInvalidParentheses("()"));
//        System.out.println(solution.removeInvalidParentheses("())"));
//        System.out.println(solution.removeInvalidParentheses("()())()"));
//        System.out.println(solution.removeInvalidParentheses("(a)())()"));
//        System.out.println(solution.removeInvalidParentheses(")("));
//        System.out.println(solution.removeInvalidParentheses("x("));
//        System.out.println(solution.removeInvalidParentheses("x)"));
//        System.out.println(solution.removeInvalidParentheses("(()"));
//        System.out.println(solution.removeInvalidParentheses(")()("));
//        System.out.println(solution.removeInvalidParentheses(")()(x"));
//        System.out.println(solution.removeInvalidParentheses(")()(("));
//        System.out.println(solution.removeInvalidParentheses(")()(()"));
//        System.out.println(solution.removeInvalidParentheses("(((k()(("));
//        System.out.println(solution.removeInvalidParentheses("(k))"));
//        System.out.println(solution.removeInvalidParentheses("(k()"));
        System.out.println(solution.removeInvalidParentheses("(k(())"));
//        System.out.println(solution.removeInvalidParentheses(")()("));
//        System.out.println(solution.removeInvalidParentheses("())())"));
    }

}
