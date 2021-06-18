package cn.iocoder.algorithm.leetcode.no0063;

/**
 * https://leetcode-cn.com/problems/unique-paths-ii/
 *
 * 动态规划
 *
 * dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
 * 特殊点是，如果 obstacleGrid[i][j] 如果是石头，就 dp[i][j] = 0 。
 */
public class Solution {

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid.length == 0) {
            return 0;
        }
        int n = obstacleGrid.length;
        int m = obstacleGrid[0].length;
        if (obstacleGrid[0][0] == 1) { // 直接是个石头
            return 0;
        }

        // dp 开始
        int[] dp = new int[m];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            // 第 0 列
            if (obstacleGrid[i][0] == 1) { // 石头
                dp[0] = 0;
            }
            // 其它列
            for (int j = 1; j < m; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                } else {
                    dp[j] = dp[j - 1] + dp[j];
                }
            }
        }
        return dp[m - 1];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[][] obstacleGrid = new int[][]{
//                {0,0,0},
//                {0,1,0},
//                {0,0,0}
//        };
//        int[][] obstacleGrid = new int[][]{
//                {0,0,0},
//                {0,0,0},
//                {0,0,0}
//        };
        int[][] obstacleGrid = new int[][]{
                {0,0,0},
                {0,0,1},
                {0,0,0}
        };
        System.out.println(solution.uniquePathsWithObstacles(obstacleGrid));
    }

}
