package com.offer;


import org.junit.jupiter.api.Test;

/**
 * 剪绳子
 * @author BaoZhou
 * @date 2020-6-21
 */

public class Q67 {
    @Test
    public void result() {
        int k = 10;
        System.out.println(cutRope(10));
    }


    public int cutRope(int target) {

        int[] dp = new int[target + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= target; i++) {
            if (i != target) { // 当i!=target时，长度为i的段也可以不分割！
                dp[i] = i;
            }
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max(dp[i], dp[j] * dp[i - j]);
            }
        }
        return dp[target];
    }
}
