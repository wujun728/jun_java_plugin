package cn.iocoder.algorithm.leetcode.no0240;

/**
 * https://leetcode-cn.com/problems/search-a-2d-matrix-ii/
 *
 * 时间复杂度 O(N + M)。
 *
 * 另外，这题还有三种解法，可以参考博客 https://leetcode-cn.com/problems/search-a-2d-matrix-ii/solution/sou-suo-er-wei-ju-zhen-ii-by-leetcode-2/ 。
 * 当然，相比这种，时间复杂度会更高，想对实现却比较复杂。感兴趣的胖友，可以自己去看看。
 */
public class Solution {

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int row = 0, col = matrix[0].length - 1;
        // 从 row = 0 ，col = matrix[0].length 为起点的原因是，能够保证
        // 1. 当 target 比当前位置大时，往下走，row++ 增大自己
        // 2. 当 target 比当前位置小时，往左走，col-- 减小自己
        while (col >= 0 && row < matrix.length) {
            if (matrix[row][col] == target) {
                return true;
            }
            if (target > matrix[row][col]) {
                row++;
            } else {
                col--;
            }
        }

        return false;
    }

}
