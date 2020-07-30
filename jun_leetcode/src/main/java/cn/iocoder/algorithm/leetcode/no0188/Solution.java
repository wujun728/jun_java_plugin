package cn.iocoder.algorithm.leetcode.no0188;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/
 *
 * 动态规划
 *
 * 和 {@link Solution} 类似。
 *
 * 不同之处，需要加上贪心算法。因为 k 如果很大，会导致出现内存溢出。贪心算法的逻辑，详细见代码。
 */
public class Solution {

    public int maxProfit(int k, int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        // 贪心算法。
        // 当交易次数，是总价格的一半，那么，只有买的股票，后面卖出有的赚，就买。
        // 因为，我们最多只会购买一半的股票。
        if (k >= prices.length / 2) {
            int max = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    max += prices[i] - prices[i - 1];
                }
            }
            return max;
        }

        // 初始化 dp 数组
        int[] sells = new int[k + 1];
        int[] buys = new int[k + 1];
        buys[0] = -prices[0]; // 持有一股时，所拥有的最大利润
        for (int j = 1; j <= k; j++) { // 其它的，我们默认初始化为负无穷大，下面计算会不断覆盖掉。千万不要默认为 0 ，这样就会出现不花钱，就持有一个股票。
            buys[j] = Integer.MIN_VALUE;
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
        System.out.println(solution.maxProfit(4, new int[]{5, 7, 2, 7, 3, 5, 3, 0})); // -5+7=2; -2+7=5; -3+5=2; 合计，9 。
    }

}
