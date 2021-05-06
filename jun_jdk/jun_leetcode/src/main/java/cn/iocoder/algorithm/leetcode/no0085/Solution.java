package cn.iocoder.algorithm.leetcode.no0085;

/**
 * https://leetcode-cn.com/problems/maximal-rectangle/
 *
 * 动态规划
 *
 * 遍历每一行，计算每一行的当前节点，最大宽度是多少，存储到 dp[i][j] 里。
 * 然后，当前节点，不断向上一行，计算最大值。
 *
 * 不算完整的动态规划，只是通过 dp 数组，记录下状态，然后后续，还是暴力计算。
 *
 * 时间复杂度 N^2 * M 。
 */
public class Solution {

    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        int max = 0;

        // 计算每一层，每个节点的宽度
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '0') {
                    continue;
                }
                // 计算当前位置宽度
                dp[i][j] = j == 0 ? 1 : dp[i][j - 1] + 1;
                // 从第 i 层开始，向第 0 层，计算最大值
                int width = dp[i][j];
                for (int k = i; k >= 0; k--) {
                    width = Math.min(width, dp[k][j]);
                    if (width == 0) {
                        break;
                    }
                    max = Math.max(max, width * (i - k + 1));
                }
            }
        }

        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.maximalRectangle(new char[][]{{'1', '1'}}));
//        System.out.println(solution.maximalRectangle(new char[][]{
//                {'1','0','1','0','0'},
//                  {'1','0','1','1','1'},
//                  {'1','1','1','1','1'},
//                  {'1','0','0','1','0'}
//        }));
        System.out.println(solution.maximalRectangle(new char[][]{
                {'0','1','1','0','1'},
                {'1','1','0','1','0'},
                {'0','1','1','1','0'},
                {'1','1','1','1','0'},
                {'1','1','1','1','1'},
                {'0','0','0','0','0'}
        }));
    }

}
