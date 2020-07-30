package cn.iocoder.algorithm.leetcode.no0073;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/set-matrix-zeroes/
 *
 * 数组，通过首行、列的 0 来标记。
 *
 * 这是目前最优解。还有一种解法，稍微慢一些。
 * 通过额外 rows、cols 数组，标记这行、列是否为 0 。
 */
public class Solution {

    public void setZeroes(int[][] matrix) {
        if (matrix.length == 0) {
            return;
        }
        int n = matrix.length;
        int m = matrix[0].length;
        boolean firstColZeroFlag = false;

        // 遍历数组，标记位
        for (int i = 0; i < n; i++) {
            // 第一列
            if (matrix[i][0] == 0) {
                firstColZeroFlag = true;
            }
            // 其他列
            for (int j = 1; j < m; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = matrix[0][j] = 0;
                }
            }
        }

        // 通过标记，设置对应行、列有 0 时，设置。
//        for (int i = 0; i < n; i++) {
//            for (int j = 1; j < m; j++) {
        for (int i = n - 1; i >= 0; i--) { // 需要倒序，否则会有问题。
            // 其他列
            for (int j = 1; j < m; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
            // 第一列
            if (firstColZeroFlag) {
                matrix[i][0] = 0;
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] matrix = new int[][]{
                {0, 1, 2, 0},
                {3, 4, 5, 2},
                {1, 3, 1, 5}
        };
        solution.setZeroes(matrix);
        System.out.println(Arrays.deepToString(matrix));
    }

}
