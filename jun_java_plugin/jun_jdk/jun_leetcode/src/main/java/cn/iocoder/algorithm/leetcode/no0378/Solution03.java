package cn.iocoder.algorithm.leetcode.no0378;

/**
 * 二分查找，不是基于下标，而是基于值。
 *
 * 基于值的二分查找，相当于区间不断缩小。胖友可以在纸面上画画。
 */
public class Solution03 {

    /**
     * 是否调试，用于打印下日志
     */
    private boolean debug = true;

    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length, m = matrix[0].length;
        int left = matrix[0][0], right = matrix[n - 1][m - 1]; // 注意，此处不是通过下标
        while (left <= right) { // left 必须大于，才能结束。
            int mid = left + ((right - left) >> 1);
            // 计算数量
            int counts = count(matrix, mid);
            if (debug) {
                System.out.println(left + "\t" +right + "\t" + mid + "\t" + counts);
            }
            // 如果 counts 数量过少，所以 left 需要增大，这样 counts 才能变大。
            if (counts < k) {
                left = mid + 1;
            // 如果 counts 数量刚好或者更大，所以 right 需要缩小，这样 counts 才能逐渐逼近 k 。
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    private int count(int[][] matrix, int target) {
        int n = 0, m = matrix[0].length - 1;
        int counts = 0;
        // 从右上角开始计数
        while (n < matrix.length && m >= 0) {
            if (matrix[n][m] > target) {
                m--;
            } else {
                counts += m + 1;
                n++;
            }
        }
        return counts;
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
        if (false) {
            System.out.println(solution.kthSmallest(new int[][]{
                    {1, 5, 9},
                    {10, 11, 13},
                    {12, 13, 15}
            }, 8));
        }
        if (false) {
            System.out.println(solution.kthSmallest(new int[][]{
                    {1, 5, 9},
                    {2, 6, 10},
                    {8, 9, 11}
            }, 4));
        }
        if (true) {
            System.out.println("result：" + solution.kthSmallest(new int[][]{
                    {1, 5, 9},
                    {10, 11, 13},
                    {12, 13, 15}
            }, 3));
        }
    }

}
