package cn.iocoder.algorithm.leetcode.no0131;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/palindrome-partitioning/
 *
 * 回溯算法
 *
 * TODO 芋艿，最优解是动态规划
 */
public class Solution {

    private List<List<String>> result = new ArrayList<>();

    public List<List<String>> partition(String s) {
        this.backtracking(s, 0, new ArrayList<>());
        return result;
    }

    private void backtracking(String s, int start, List<String> current) {
        // 结束，添加到结果
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < s.length(); i++) {
            if (!this.isPalindrome(s, start, i)) {
                continue;
            }
            current.add(s.substring(start, i + 1));
            this.backtracking(s, i + 1, current);
            current.remove(current.size() - 1);
        }
    }

    private boolean isPalindrome(String s, int start, int end) {
        while (start <= end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.partition("aab"));
    }

}
