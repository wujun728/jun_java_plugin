package cn.iocoder.algorithm.leetcode.no0062;

/**
 * https://leetcode-cn.com/problems/unique-paths/
 *
 * 动态规划
 *
 * 路径数，dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
 *
 * 考虑到只能向右或者向下走，所以可以进一步数组压缩，从二维，变成一唯。
 */
public class Solution {

    public int uniquePaths(int m, int n) {
        if (m == 0 || n == 0) {
            return 0;
        }

        // 创建数组，设置默认肯定有 1 条路径
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n; j++) { // 第一列，只会一直向下
                dp[j] = dp[j - 1] + dp[j];
            }
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.uniquePaths(3, 2));
//        System.out.println(solution.uniquePaths(7, 3));
        System.out.println(solution.uniquePaths(3, 3));
    }

}
