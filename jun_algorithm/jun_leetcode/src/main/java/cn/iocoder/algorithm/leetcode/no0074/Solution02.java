package cn.iocoder.algorithm.leetcode.no0074;

/**
 * 二分查找
 */
public class Solution02 {

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) {
            return false;
        }
        int row = matrix.length, col = matrix[0].length;

        // 二分查找
        int left = 0, right = row * col - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            int value = matrix[mid / col][mid % col];
            if (value == target) {
                return true;
            }
            if (value > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(solution.searchMatrix(new int[][]{
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 50}
        }, 3));
    }

}
