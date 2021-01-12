package com.leetcode.middle.sort;

import org.junit.jupiter.api.Test;

/**
 * 搜索二维矩阵 II
 *
 * @author BaoZhou
 * @date 2019/5/8
 */
public class SearchMatrix {
    @Test
    void test() {
        int[][] nums = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        System.out.println(searchMatrix(nums, 100));
        System.out.println(searchMatrix(nums, -1));
        System.out.println(searchMatrix(nums, 14));
        System.out.println(searchMatrix(nums, 30));
        System.out.println(searchMatrix(nums, 9));
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) {
            return false;
        }

        if (matrix[0].length == 0) {
            return false;
        }

        int start_x = matrix.length - 1;
        int start_y = 0;

        while (start_x >= 0 && start_y < matrix[0].length) {
            if (matrix[start_x][start_y] == target) {
                return true;
            } else if (matrix[start_x][start_y] > target) {
                start_x--;
            } else {
                start_y++;
            }
        }
        return false;
    }


}
