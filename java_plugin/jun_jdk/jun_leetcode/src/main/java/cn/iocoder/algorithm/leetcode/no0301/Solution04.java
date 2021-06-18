package cn.iocoder.algorithm.leetcode.no0301;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 在 {@link Solution03} 的基础上，增加剪枝，提前计算好需要删除的左右括号的数量
 *
 * 目前最优解。
 */
public class Solution04 {

    private Set<String> result = new HashSet<>();

    public List<String> removeInvalidParentheses(String s) {
        // 计算左右括号，需要删除的数量
        int leftDelCount = 0;
        int rightDeleCount = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                leftDelCount++;
            } else if (ch == ')') {
                if (leftDelCount > 0) { // 和 ( 左括号配对
                    leftDelCount--;
                } else { // 目前无 ( 左括号可以匹配，所以需要删除。
                    rightDeleCount++;
                }
            }
        }


        // 递归回溯
        this.backtracking(s, 0, 0, 0, new StringBuffer(s.length()), leftDelCount, rightDeleCount);
        return new ArrayList<>(result);
    }

    private void backtracking(String s, int index, int leftCount, int rightCount, StringBuffer current,
                              int leftDelCount, int rightDeleCount) {
        // 遍历完字符串
        if (index == s.length()) {
            if (leftCount == rightCount) { // 说明符合条件
                result.add(current.toString());
            }
            return;
        }
        int length = current.length();

        // 非左右括号
        char ch = s.charAt(index);
        if (ch != '(' && ch != ')') {
            current.append(ch);
            this.backtracking(s, index + 1, leftCount, rightCount, current, leftDelCount, rightDeleCount);
            current.deleteCharAt(length);
            return;
        }

        // 直接忽略当前的括号，往下递归
        if (ch == '(' && leftDelCount > 0) {
            this.backtracking(s, index + 1, leftCount, rightCount, current, leftDelCount - 1, rightDeleCount);
        } else if (ch == ')' && rightDeleCount > 0) {
            this.backtracking(s, index + 1, leftCount, rightCount, current, leftDelCount, rightDeleCount - 1);
        }

        // 如果是左括号
        if (ch == '(') {
            current.append(ch);
            this.backtracking(s, index + 1, leftCount + 1, rightCount, current, leftDelCount, rightDeleCount);
            current.deleteCharAt(length);
            return;
        }

        // 如果是右括号，必须数量比左括号小，不然就是不合法的表达式
        if (leftCount > rightCount) {
            current.append(ch);
            this.backtracking(s, index + 1, leftCount, rightCount + 1, current, leftDelCount, rightDeleCount);
            current.deleteCharAt(length);
        }
    }

    public static void main(String[] args) {
        Solution04 solution = new Solution04();
        System.out.println(solution.removeInvalidParentheses(")(f"));
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
//        System.out.println(solution.removeInvalidParentheses("(k(())"));
//        System.out.println(solution.removeInvalidParentheses(")()("));
//        System.out.println(solution.removeInvalidParentheses("())())"));
    }

}
