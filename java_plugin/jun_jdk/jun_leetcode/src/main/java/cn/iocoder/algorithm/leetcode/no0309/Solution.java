package cn.iocoder.algorithm.leetcode.no0309;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
 *
 * 动态规划
 *
 * 不持有股票 sell[i] = Math.max(sell[i - 1], cooldown[i]);
 * 冷冻期 cooldown[i] = Math.max(cooldown[i - 1], buy[i] + nums[i]);
 * 持有一个股票 buy[i] = Math.max(buy[i - 1], sell[i - 1] - nums[i]);
 *
 * 可以进一步压缩，不使用数组，每一轮使用一套变量，然后赋值。
 */
public class Solution {

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int sell = 0;
        int cooldown = 0;
        int buy = -prices[0];

        // dp 开始
        for (int i = 1; i < prices.length; i++) {
            // 本轮计算
            int price = prices[i];
            int sellNow = Math.max(sell, cooldown);
            int cooldownNow = Math.max(cooldown, buy + price);
            int buyNow = Math.max(buy, sell - price);
            // 赋值
            sell = sellNow;
            cooldown = cooldownNow;
            buy = buyNow;
        }
        return Math.max(sell, cooldown);
    }

}
