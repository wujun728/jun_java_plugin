package cn.iocoder.algorithm.leetcode.no0343;

/**
 * https://leetcode-cn.com/problems/integer-break
 *
 * 动态规划
 *
 * 表达式 dp[i] = Math.max(dp[i - j] * j, dp[i])
 */
public class Solution {

    public int integerBreak(int n) {
        // 因为必须分解成对应的数字
        if (n == 2) {
            return 1;
        }
        if (n == 3) {
            return 3;
        }

        // DP 开始
        int[] dp = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i] = i;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max(dp[i - j] * j, dp[i]);
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.integerBreak(3));
    }

}
