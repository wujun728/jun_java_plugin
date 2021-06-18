package cn.iocoder.algorithm.leetcode.no0122;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
 *
 * 贪心算法
 *
 * 只有第二天有的赚，就买卖
 */
public class Solution {

    public int maxProfit(int[] prices) {
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            int diffPrice = prices[i] - prices[i - 1];
            if (diffPrice > 0) {
                sum += diffPrice;
            }
        }
        return sum;
    }

}
