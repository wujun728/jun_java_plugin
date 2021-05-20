package cn.iocoder.algorithm.leetcode.no0123;

/**
 * 和 {@link Solution} 表达式差不多，差别在于用二维码数组，通过第二个维度，表示持有股票数量。
 *
 * 相对来说，拓展性会更好。例如说，以后可以允许同时持有多股。
 */
public class Solution02 {

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int k = 2; // 最大交易次数，只能两次
        // 第一维度 k ，表示当前【买入股票】的次数；
        // 第二维度 2 ，表示 0 - 未持有，1 - 持有 1 股
        // 值为最大利润
        int[][] dp = new int[k + 1][2];
        // 初始化第一个股票的处理
        dp[1][1] = -prices[0]; // 买入
        dp[0][1] = Integer.MIN_VALUE; // 相当于赋值为空，避免直接认为持有一股时，利润为 0 。
        dp[2][1] = -prices[0]; // 买入 + 卖出 + 买入

        // 遍历，进行买卖
        for (int i = 1; i < prices.length; i++) {
            for (int j = 0; j <= k; j++) {
                // 尝试卖出
                dp[j][0] = Math.max(dp[j][0], dp[j][1] + prices[i]);
                // 尝试买入
                if (j > 0) {
                    dp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - prices[i]);
                }
            }
        }

        return Math.max(dp[1][0], dp[2][0]);
    }

}
