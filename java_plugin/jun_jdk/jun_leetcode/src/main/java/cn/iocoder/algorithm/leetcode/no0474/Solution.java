package cn.iocoder.algorithm.leetcode.no0474;

/**
 * https://leetcode-cn.com/problems/ones-and-zeroes/
 *
 * 动态规划
 *
 * 表达式，dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones]);
 *
 * 其中，zeros 和 ones 是当前字符串的 0 和 1 的数量，而 dp[i][j] 表示 i 和 j 个 0 和 1 的情况下，可以满足最多多少个字符串。
 */
public class Solution {

    public int findMaxForm(String[] strs, int m, int n) {
        if (m == 0 && n == 0) {
            return 0;
        }

        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
            // 计算 0 和 1 的数量
            int zeros = 0;
            int ones = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }

            // 倒序 dp ，不然会覆盖值。
            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
                }
            }
        }
        return dp[m][n];
    }

}
