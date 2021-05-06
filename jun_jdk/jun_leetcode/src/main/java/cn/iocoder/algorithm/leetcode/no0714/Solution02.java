package cn.iocoder.algorithm.leetcode.no0714;

/**
 * 在 {@link Solution} 上优化
 *
 * 因为不限制购买次数，所以即使出售后，我们还是可以购买股票噢。
 */
public class Solution02 {

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
            sell = Math.max(sell, buy + price - fee);
            buy = Math.max(buy, sell - price);
        }
        return sell;
    }

}
