package cn.iocoder.algorithm.leetcode.no0054;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/spiral-matrix/
 *
 * 数组
 *
 * 模拟，右下左上走啊走啊。
 */
public class Solution {

    public List<Integer> spiralOrder(int[][] matrix) {
        int n = matrix.length - 1;
        if (n < 0) {
            return Collections.emptyList();
        }
        int m = matrix[0].length - 1;
        List<Integer> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (true) {
            // 行，从左到右
            for (int k = j; k <= m; k++) {
                result.add(matrix[i][k]);
            }
            i++;
            if (i > n) {
                break;
            }
            // 列，从上到下
            for (int k = i; k <= n; k++) {
                result.add(matrix[k][m]);
            }
            m--;
            if (j > m) {
                break;
            }
            // 行，从右到左
            for (int k = m; k >= j; k--) {
                result.add(matrix[n][k]);
            }
            n--;
            if (i > n) {
                break;
            }
            // 列，从下到上
            for (int k = n; k >= i; k--) {
                result.add(matrix[k][j]);
            }
            j++;
            if (j > m) {
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.spiralOrder(new int[][]{
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9 }
//        }));
//        System.out.println(solution.spiralOrder(new int[][]{
//                {1, 2, 3},
//        }));
//        System.out.println(solution.spiralOrder(new int[][]{
//                {1},
//                {2},
//                {3},
//                {4},
//        }));
//        System.out.println(solution.spiralOrder(new int[][]{
//                {1},
//                {2},
//                {3},
//                {4},
//        }));
        System.out.println(solution.spiralOrder(new int[][]{
                {2, 5, 8},
                {4, 0, 1},
        }));
    }

}
