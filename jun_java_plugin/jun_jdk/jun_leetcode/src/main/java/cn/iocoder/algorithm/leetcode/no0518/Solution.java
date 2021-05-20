package cn.iocoder.algorithm.leetcode.no0518;

/**
 * https://leetcode-cn.com/problems/coin-change-2/
 *
 * 动态规划
 *
 * dp[i] = dp[i] + dp[i - num]
 *
 * 不同的是，按照 coins 进行遍历
 */
public class Solution {

    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1; // 0 个硬币，肯定有 1 种换法
        for (int coin : coins) { // 注意，基于 coin 遍历，不然会出现重复结果。
            for (int i = coin; i <= amount; i++) {
                    dp[i] = dp[i] + dp[i - coin];
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        // 1：1
        // 2：1、1 ；2
        // 3：1、1、1；1、2
        // 4：1、1、1、1；
        System.out.println(solution.change(5, new int[]{1, 2, 5}));

        System.out.println(solution.change(3, new int[]{2}));
        System.out.println(solution.change(10, new int[]{10}));
    }

}
