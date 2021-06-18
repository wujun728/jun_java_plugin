package cn.iocoder.algorithm.leetcode.no0050;

/**
 * https://leetcode-cn.com/problems/powx-n/
 *
 * 折半 + 递归实现
 */
public class Solution {

    public double myPow(double x, int n) {
        // 0 次方，直接返回 1
        if (n == 0) {
            return 1;
        }
        // 1 次方，返回本身
        if (n == 1) {
            return x;
        }
        // 负次方
        if (n < 0) {
            if (n == Integer.MIN_VALUE) { // 注意，因为 Integer.MIN_VALUE 反过来，超过 int 最大值。
                return 1 / x * myPow(1 / x, Integer.MAX_VALUE);
            } else {
                return myPow(1 / x, -n);
            }
        }

        // 递归
        if ((n & 1) == 1) {
            return x * myPow(x * x, n / 2);
        } else {
            return myPow(x * x, n / 2);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.myPow(2.00000, 10));
//        System.out.println(solution.myPow(2.10000, 3));
//        System.out.println(solution.myPow(2.10000, -2));
//        System.out.println(solution.myPow(2, -2));
        System.out.println(solution.myPow(2, Integer.MIN_VALUE));
    }

}
