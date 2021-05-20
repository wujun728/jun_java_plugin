package cn.iocoder.algorithm.leetcode.no0050;

/**
 * 和 {@link Solution} 一样的思路，只是从递归，改成了循环
 */
public class Solution02 {

    public double myPow(double x, int n) {
        // 0 次方，直接返回 1
        if (n == 0) {
            return 1;
        }

        double result = 1;
        // 负次方
        if (n < 0) {
            if (n == Integer.MIN_VALUE) { // 注意，因为 Integer.MIN_VALUE 反过来，超过 int 最大值。
                result = 1 / x; // 先乘以掉 Integer.MIN_VALUE 反过来的 1 。
                x = 1 / x;
                n = Integer.MAX_VALUE;
            } else {
                x = 1 / x;
                n = -n;
            }
        }

        // 递归
        while (n > 1) {
            // 奇数，把 x 乘以到 result 中
            if ((n & 1) == 1) {
                result = result * x;
            }
            // 平方
            x = x * x;
            n = n >> 1;
        }

        return result * x;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.myPow(2.00000, 10));
        System.out.println(solution.myPow(2.10000, 3));
        System.out.println(solution.myPow(2.10000, -2));
        System.out.println(solution.myPow(2, -2));
        System.out.println(solution.myPow(2, Integer.MIN_VALUE));
    }

}
