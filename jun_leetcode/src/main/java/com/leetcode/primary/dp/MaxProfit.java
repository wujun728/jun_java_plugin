package com.leetcode.primary.dp;

/**
 * 买卖股票的最佳时机
 *
 * @author: BaoZhou
 * @date : 2018/12/17 22:05
 */
class MaxProfit {
    public static void main(String[] args) {
        int[] nums = {7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit(nums));
    }

    public static int maxProfit(int[] prices) {
        int min =Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i = 0; i < prices.length; i++) {
            if(prices[i] < min){
                min = prices[i];
            }
            if(prices[i] - min > maxprofit)
            {
                maxprofit = prices[i]-min;
            }
        }
        return maxprofit;

    }
}