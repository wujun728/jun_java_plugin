package cn.iocoder.algorithm.leetcode.no0766;

/**
 * https://leetcode-cn.com/problems/toeplitz-matrix/description/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
 */
public class Solution {

    public boolean isToeplitzMatrix(int[][] matrix) {
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                // 和左上角比较
                if (matrix[i][j] != matrix[i - 1][j -1]) {
                    return false;
                }
            }
        }

        return true;
    }

}
