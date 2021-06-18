package cn.iocoder.algorithm.leetcode.no0746;

/**
 * https://leetcode-cn.com/problems/min-cost-climbing-stairs/
 *
 * 动态规划
 *
 * f[2] = Math.min(f[1], f[0]) + cost[2]
 */
public class Solution {

    public int minCostClimbingStairs(int[] cost) {
        if (cost.length == 0) {
            return 0;
        }
        if (cost.length == 1) {
            return cost[0];
        }
        int f0 = cost[0];
        int f1 = cost[1];
        for (int i = 2; i < cost.length; i++) {
            // 计算当前楼梯，最小值
            int f2 = Math.min(f0, f1) + cost[i];
            // 切换值
            f0 = f1;
            f1 = f2;
        }
        return Math.min(f0, f1);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.minCostClimbingStairs(new int[]{10, 15, 20}));
        System.out.println(solution.minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));
    }

}
