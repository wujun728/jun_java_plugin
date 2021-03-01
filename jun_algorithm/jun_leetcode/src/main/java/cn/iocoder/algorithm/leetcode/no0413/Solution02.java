package cn.iocoder.algorithm.leetcode.no0413;

/**
 * 动态规划
 *
 * 表达式
 *
 * 每个子等差数列，每多一个数字，会多比上一次多 1 的组合。
 * 例如说，3 个等差数，组合数为 1 ；4 个等差数，组合数为 2 ；5 个等差数为 3 。
 *
 * dp = dp + 1 。
 */
public class Solution02 {

    public int numberOfArithmeticSlices(int[] A) {
        int sum = 0; // 总计
        int dp = 0;
        for (int i = 2; i < A.length; i++) {
            if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
                //
                dp = dp + 1;
                //
                sum = sum + dp;
            } else {
                dp = 0;
            }
        }
        return sum;
    }

}
