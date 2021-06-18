package cn.iocoder.algorithm.leetcode.no0123;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/
 *
 * 动态规划
 *
 * sell[i][k] = Math.max(sell[i - 1][k], buy[i - 1][k - 1] + price)
 * buy[i][k] = Math.max(buy[i - 1][k], sell[i - 1][k] - price)
 */
public class Solution {

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        // 初始化 dp 数组
        int k = 2; // 可交易的笔数
        int[] sells = new int[k + 1];
        int[] buys = new int[k + 1];
        buys[0] = -prices[0]; // 持有一股时，所拥有的最大利润
        for (int j = 1; j <= k; j++) { // 其它的，我们默认初始化为负无穷大，下面计算会不断覆盖掉。千万不要默认为 0 ，这样就会出现不花钱，就持有一个股票。
            buys[1] = Integer.MIN_VALUE;
        }

        // 开始 dp
        for (int i = 1; i < prices.length; i++) {
            // 本轮 dp
            int price = prices[i];
            int[] nowSells = new int[k + 1];
            int[] nowBuys = new int[k + 1];
            for (int j = 0; j <= k; j++) {
                if (j >= 1) {
                    nowSells[j] = Math.max(sells[j], buys[j - 1] + price);
                } else {
                    nowSells[j] = sells[j];
                }
                nowBuys[j] = Math.max(buys[j], sells[j] - price);
            }
            // 赋值
            sells = nowSells;
            buys = nowBuys;
        }

        // 输出结果
        int max = 0;
        for (int j = 0; j <= k; j++) {
            max = Math.max(max, sells[j]);
        }
        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxProfit(new int[]{1, 2, 3, 4, 5}));
    }

}
