package com.leetcode.middle.dp;

/**
 * 不同路径
 * 动态规划
 *
 * @author BaoZhou
 * @date 2018/12/21
 */
public class UniquePaths {
    public static void main(String[] args) {

        System.out.println(uniquePaths(7, 3));
    }

    public static int uniquePaths(int m, int n) {
        int[][] result = new int[n][m];
        result[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i > 0) {
                    result[i][j] += result[i - 1][j];
                }
                if (j > 0) {
                    result[i][j] += result[i][j - 1];
                }
            }
        }
        //outArray(result);
        return result[n - 1][m - 1];
    }

    public static void outArray(int[][] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.println();
            for (int j = 0; j < nums[i].length; j++) {
                System.out.print(nums[i][j] + " ");
            }
        }
    }
}
