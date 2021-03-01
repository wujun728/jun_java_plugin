package cn.iocoder.algorithm.leetcode.no0022;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/generate-parentheses
 *
 * 回溯算法
 */
public class Solution {

    private List<String> results = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        this.backtracking(0, 0, n, "");
        return results;
    }

    private void backtracking(int leftCounts, int rightCounts, int n, String current) {
        if (leftCounts == n && rightCounts == n) {
            results.add(current);
            return;
        }

        // 左括号 (
        if (leftCounts < n) {
            this.backtracking(leftCounts + 1, rightCounts, n, current + "(");
        }
        // 右括号 )
        if (rightCounts < leftCounts) {
            this.backtracking(leftCounts, rightCounts + 1, n, current + ")");
        }
    }

}
