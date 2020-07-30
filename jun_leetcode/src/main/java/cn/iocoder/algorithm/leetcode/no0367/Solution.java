package cn.iocoder.algorithm.leetcode.no0367;

/**
 * https://leetcode-cn.com/problems/valid-perfect-square/
 *
 * 二分查找
 */
public class Solution {

    public boolean isPerfectSquare(int num) {
        if (num <= 1) {
            return true;
        }

        int left = 1, right = num / 2;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            int result = num / mid;
            if (mid == result) {
                right = mid;
                break;
            }
            if (result > mid) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return right * right == num;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        solution.isPerfectSquare(4);
        for (int i = 0; i <= 100; i ++) {
            System.out.println(i + "\t" + solution.isPerfectSquare(i));
        }
    }

}
