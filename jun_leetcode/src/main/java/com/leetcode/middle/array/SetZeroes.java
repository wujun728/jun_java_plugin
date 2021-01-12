package com.leetcode.middle.array;

import com.leetcode.leetcodeutils.PrintUtils;

/**
 * 矩阵置零
 * 只需要两个额外的布尔变量而已
 * @author BaoZhou
 * @date 2018/12/22
 */
public class SetZeroes {
    public static void main(String[] args) {
        int[][] nums3 = {{0, 1, 2, 0},
                {3, 4, 5, 2},
                {1, 3, 1, 5}};
        int[][] nums = {{0,0,0,5},
                {4,3,1,4},
                {0,1,1,4},
                {1,2,1,3},
                {0,0,1,1}};
        int[][] nums2 = {{1, 0, 3}};
        setZeroes(nums);
        PrintUtils.printMatrix(nums);
    }

    public static void setZeroes(int[][] matrix) {
        boolean line = false, column = false;

        //记录第一列数据
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                column = true;
                break;
            }
        }

        //记录第一行数据
        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                line = true;
                break;
            }
        }

        //记录其他行数据到第一行第一列
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        //如果该列为0，就把整列改成0
        for (int i = 1; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                for (int j = 0; j < matrix.length; j++) {
                    matrix[j][i] = 0;
                }
            }
        }

        //如果该行为0，就把整行改成0
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j] = 0;
                }
            }
        }


        //对第一行列进行处理
        if (column) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }

        if (line) {
            for (int i = 0; i < matrix[0].length; i++) {
                matrix[0][i] = 0;
            }
        }
    }
}


