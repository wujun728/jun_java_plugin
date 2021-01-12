package com.leetcode.leetcodeutils;

import java.util.List;

/**
 * @author BaoZhou
 * @date 2018/12/22
 */
public class PrintUtils {
    public static void printArrays(List<List<Integer>> nums) {
        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.get(i).size(); j++) {
                System.out.print(nums.get(i).get(j) + " ");
            }
            System.out.println(" ");
        }
    }

    public static void printStringArrays(List<List<String>> nums) {
        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.get(i).size(); j++) {
                System.out.print(nums.get(i).get(j) + " ");
            }
            System.out.println(" ");
        }
    }


    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
}
