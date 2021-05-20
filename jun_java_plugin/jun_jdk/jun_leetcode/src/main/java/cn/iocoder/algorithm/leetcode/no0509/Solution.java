package cn.iocoder.algorithm.leetcode.no0509;

/**
 * https://leetcode-cn.com/problems/fibonacci-number/
 *
 * 动态规划
 */
public class Solution {

    public int fib(int N) {
        if (N == 0) {
            return 0;
        }
        if (N == 1) {
            return 1;
        }
        int f0 = 0;
        int f1 = 1;
        for (int i = 2; i <= N; i++) {
            // 计算当前节点的只
            int f2 = f0 + f1;
            // 赋值
            f0 = f1;
            f1 = f2;
        }
        return f1;
    }

}
