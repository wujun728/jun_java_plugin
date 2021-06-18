package cn.iocoder.algorithm.leetcode.no0085;

import java.util.Arrays;

/**
 * 动态规划
 *
 * 在 {@link Solution} 基础上改进。
 *
 * 时间复杂度 N * M 。
 *
 * 当 i, j 为 1 时，
 * * left[j] ，当前层，距离连续 1 最左的位置。
 * * right[j], 当前层，距离连续 1 最右的位置。
 * * height[j]，当前层，向上连续 1 的高度。
 *
 * 当 i, j 为 0 时，
 * * left[j] = 0
 * * right[j] = n
 * * height[j] = 0
 *
 * 每一层，都计算 Math.max(max, height[j] * (right[j] - left[j]))
 *
 * 参考 https://leetcode-cn.com/problems/maximal-rectangle/solution/zui-da-ju-xing-by-leetcode/ 方法四。
 */
public class Solution02 {

    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int n = matrix.length;
        int m = matrix[0].length;

        // 初始化 lefts、rights、heights 数组。
        int[] lefts = new int[m];
        int[] rights = new int[m];
        int[] heights = new int[m];
        Arrays.fill(rights, m);

        int max = 0;
        for (int i = 0; i < n; i++) {
            // 当前层的 left 和 right
            int currentLeft = 0;
            int currentRight = m;
            // heights
            for (int j = 0; j < m;j ++) {
                if (matrix[i][j] == '1') {
                    heights[j]++;
                } else {
                    heights[j] = 0;
                }
            }
            // lefts
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '1') {
                    // max 的原因，需要基于上一层的最左(lefts[j])和当前层的最左(currentLeft) 的更靠右，即 max 。这样，才是矩阵。
                    lefts[j] = Math.max(lefts[j], currentLeft);
                } else {
                    // left
                    lefts[j] = 0;
                    currentLeft = j + 1; // 注意，这里一定要 j + 1 ，否则下一个节点求 max ，会不对。
                }
            }
            // rights
            for (int j = m - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    // right ：min 的原因，需要基于上一层的最右(rights[j])和当前层的最右(currentRight)的更靠左，即 min 。这样，才是矩阵。
                    rights[j] = Math.min(rights[j], currentRight);
                } else {
                    rights[j] = m;
                    currentRight = j; // 注意，这里一定要 j ，否则下一个节点求 min ，会不对。
                }
            }
            // max
            for (int j = 0; j < m; j++) {
                max = Math.max(max, heights[j] * (rights[j] - lefts[j]));
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
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
