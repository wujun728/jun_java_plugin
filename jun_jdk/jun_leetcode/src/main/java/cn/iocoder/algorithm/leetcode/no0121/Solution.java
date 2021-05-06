package cn.iocoder.algorithm.leetcode.no0121;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 *
 * 贪心算法
 *
 * 顺序计算，利润的最大值
 */
public class Solution {

    public int maxProfit(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int max = 0; // 差价最大值
        int buy = prices[0]; // 计算到当前，购买的最小值
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(max, prices[i] - buy);
            buy = Math.min(buy, prices[i]);
        }
        return max;
    }

}
