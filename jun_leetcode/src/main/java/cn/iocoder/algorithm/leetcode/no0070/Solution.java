package cn.iocoder.algorithm.leetcode.no0070;

/**
 * https://leetcode-cn.com/problems/climbing-stairs/
 *
 * 动态规划
 *
 * 表达式是，f(n) = f(n - 1) + f(n - 2)
 */
public class Solution {

    public int climbStairs(int n) {
        if (n <= 1) {
            return n;
        }
        int f0 = 1;
        int f1 = 1;
        for (int i = 2; i <= n; i++) {
            // 计算 f0 和 f1 到下一个 f2 的方案
            int f2 = f0 + f1;
            // 设置 f0、f1
            f0 = f1;
            f1 = f2;
        }
        return f1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.climbStairs(2));
//        System.out.println(solution.climbStairs(3));
//        System.out.println(solution.climbStairs(4));
    }

}
