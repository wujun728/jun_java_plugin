package cn.iocoder.algorithm.leetcode.no0074;

/**
 * https://leetcode-cn.com/problems/search-a-2d-matrix/
 *
 * 暴力求解
 */
public class Solution {

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) {
            return false;
        }
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (ints[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

}
