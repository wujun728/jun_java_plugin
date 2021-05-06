package cn.iocoder.algorithm.leetcode.no0064;

/**
 * https://leetcode-cn.com/problems/minimum-path-sum/
 *
 * 动态规划
 *
 * dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + dp[i][j]
 *
 * 因为不会重复遍历，所以直接使用 grid 存储结果.
 */
public class Solution {

    public int minPathSum(int[][] grid) {
        int n = grid.length;
        if (n == 0) {
            return 0;
        }
        int m = grid[0].length;

        // 逐行，计算结果
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int above = i > 0 ? grid[i - 1][j] : Integer.MAX_VALUE;
                int left = j > 0 ? grid[i][j - 1] : Integer.MAX_VALUE;
                grid[i][j] = Math.min(above, left) + grid[i][j];
            }
        }
        return grid[n - 1][m - 1];
    }

}
