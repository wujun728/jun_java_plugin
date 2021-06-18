package cn.iocoder.algorithm.leetcode.no0714;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
 *
 * 动态规划
 *
 * sell[i] = Math.max(sell[i - 1], buy[i - 1] + nums[i] - fee)
 * buy[i] = Math.max(buy[i - 1], sell[i - 1] - nums[i])
 */
public class Solution {

    public int maxProfit(int[] prices, int fee) {
        // 初始化
        if (prices.length == 0) {
            return 0;
        }
        int sell = 0;
        int buy = -prices[0];

        // 开始 dp
        for (int i = 1; i < prices.length; i++) {
            // 当前轮
            int price = prices[i];
            int sellNow = Math.max(sell, buy + price - fee);
            int buyNow = Math.max(buy, sell - price);
            // 赋值
            sell = sellNow;
            buy = buyNow;
        }
        return sell;
    }

}
