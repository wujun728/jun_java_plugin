package cn.iocoder.algorithm.leetcode.no0343;

/**
 * https://leetcode-cn.com/problems/integer-break
 *
 * 动态规划
 *
 * 和 {@link Solution} 类似，简化逻辑
 *
 * 表达式 dp[i] = Math.max(dp[i - j] * j, dp[i], (i - j) * j)
 */
public class Solution02 {

    public int integerBreak(int n) {
        // DP 开始
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = this.max(dp[i - j] * j, dp[i], (i - j) * j);
            }
        }
        return dp[n];
    }

    private int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.integerBreak(3));
//        System.out.println(solution.integerBreak(2));
        System.out.println(solution.integerBreak(10));
    }

}
