package cn.iocoder.algorithm.leetcode.no0322;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/coin-change/
 *
 * 动态规划
 *
 * dp[i] = Math.min(dp[i], dp[i - num] + 1)
 *
 * 其中, 要求 dp[i - num] != -1 。
 */
public class Solution {

    private static final int NULL = -1;

    public int coinChange(int[] coins, int amount) {
        // 初始化 dp 数组
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, NULL);
        dp[0] = 0;

        // dp 开始
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin < 0) {
                    continue;
                }
                if (dp[i - coin] == NULL) {
                    continue;
                }
                if (dp[i] == -1) {
                    dp[i] = dp[i - coin] + 1;
                } else {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.coinChange(new int[]{1, 2, 5}, 11));
//        System.out.println(solution.coinChange(new int[]{2}, 3));
        System.out.println(solution.coinChange(new int[]{}, 6));
    }

}
