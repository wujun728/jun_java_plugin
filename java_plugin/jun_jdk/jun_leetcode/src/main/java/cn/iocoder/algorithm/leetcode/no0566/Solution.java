package cn.iocoder.algorithm.leetcode.no0566;

/**
 * https://leetcode-cn.com/problems/reshape-the-matrix/
 */
public class Solution {

    public int[][] matrixReshape(int[][] nums, int r, int c) {
        // 校验矩阵
        int n = nums.length;
        if (n == 0) {
            return nums;
        }
        int m = nums[0].length;
        if (m == 0) {
            return nums;
        }
        if (n * m != r * c) {
            return nums;
        }

        int[][] reshape = new int[r][c];
        int p = 0, q = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                reshape[p][q] = nums[i][j];
                // 设置 p、q
                q++;
                if (q == c) {
                    p++;
                    q = 0;
                }
            }
        }

        return reshape;
    }


}
