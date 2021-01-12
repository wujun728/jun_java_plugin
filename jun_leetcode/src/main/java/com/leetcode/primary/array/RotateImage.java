package com.leetcode.primary.array;

/**
 * 旋转图像
 *
 * @author: BaoZhou
 * @date : 2018/12/10 0:58
 */

public class RotateImage {

    public static void main(String[] args) {
        int[][] nums = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        //int[] nums = {1, 1, 2};
        int len = nums.length;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                System.out.print(nums[i][j] + " ");
            }
            System.out.println(" ");
        }
        rotate(nums);

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                System.out.print(nums[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    public static void rotate(int[][] matrix) {
        int length = matrix.length;
        for (int i = 0; i < length  / 2; i++) {
            for (int j = i; j < length - i -1 ; j++) {
                //四个互换
                int t = matrix[i][j];
                matrix[i][j] = matrix[length - j - 1][i];
                matrix[length - j - 1][i] = matrix[length - i - 1][length - j - 1];
                matrix[length - i - 1][length - j - 1] = matrix[j][length - i - 1];
                matrix[j][length - i - 1] = t;
            }

        }

    }
}

