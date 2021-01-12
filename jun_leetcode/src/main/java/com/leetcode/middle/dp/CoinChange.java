package com.leetcode.middle.dp;

/**
 * 零钱兑换
 *
 * @author: BaoZhou
 * @date : 2018/12/21 16:54
 */
class CoinChange {
    public static void main(String[] args) {

        int[] nums = {3, 2, 1, 0, 4};
        int[] nums1 = {1, 2, 5};
        int[] nums2 = {2};
        System.out.println(coinChange(nums1, 11));
    }

    public static int coinChange(int[] coins, int amount) {
        int[] result = new int[amount + 1];
        result[0] = 0;
        for (int i = 1; i <= amount; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                int lastResult = i - coins[j];
                if (lastResult >= 0 && result[lastResult] != -1) {
                    if (result[lastResult] < min) {
                        min = result[lastResult];
                    }
                }
            }
            if (min != Integer.MAX_VALUE) {
                result[i] = min + 1;
            } else {
                result[i] = -1;
            }
        }
        return result[amount];
    }
}