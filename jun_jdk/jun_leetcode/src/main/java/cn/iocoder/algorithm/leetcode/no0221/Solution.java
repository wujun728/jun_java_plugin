package cn.iocoder.algorithm.leetcode.no0221;

/**
 * https://leetcode-cn.com/problems/maximal-square/
 *
 * 动态规划
 *
 * dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j -1], dp[i][j - 1]) + 1 ，如果当前 i,j 是 1 的话。
 * 一般来说，如果要形成正方向，就是左上角 dp[i - 1][j - 1] 是正方向，那么如果自己也是 1 ，也是有可能形成一个正方向。
 * 但是，万一自己这行/列，有一个不是 1 ，那么就无法和 dp[i - 1][j - 1] 形成更大的正方向，只能取 dp[i - 1][j] 或者 dp[i][j - 1] 更小的值 + 1 。
 * 大体想想下，哈哈哈。
 *
 * 另外，还有两个解法，可以参考 https://leetcode-cn.com/problems/maximal-square/solution/zui-da-zheng-fang-xing-by-leetcode/
 * 1. 暴力法，对应方法一。
 * 2. 动态规划，增加内存压缩，对应方法三。
 */
public class Solution {

    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n + 1][m + 1]; // 多 + 1 。下面编码会方便一些，不过带来的坏处，就是占用内存。
        int maxLen = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (matrix[i - 1][j - 1] == '1') { // 因为我们在 dp 多 + 1 ，这样方便 dp 的判断。所以，此处实际判断的是 matrix 的当前节点。
                    // 计算 dp
                    dp[i][j] = this.min(dp[i - 1][j - 1], dp[i][j - 1], dp[i - 1][j]) + 1;
                    // 计算最大边
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
            }
        }
        return maxLen * maxLen;
    }

    private int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

}
