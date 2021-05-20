package cn.iocoder.algorithm.leetcode.no0279;

/**
 * 动态规划
 *
 * dp[i] = Math.min(dp[i], dp[i - j * j] + 1)
 *      for (i) 循环时，会首先认为 dp[i] 为 i ，即所有都是 1 。
 */
public class Solution02 {

    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            // 首先假设，全部使用 1 凑出 i
            dp[i] = i;
            // 顺序使用 j * j 作为平方
            for (int j = 1; i - j * j >= 0; j++) {
                // 因为前面 dp[i] = i 这段在，所以 dp[i - j * j] 是一定有值的。
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }

}
