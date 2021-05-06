package cn.iocoder.algorithm.leetcode.no0198;

/**
 * https://leetcode-cn.com/problems/house-robber/
 *
 * 动态规划
 *
 * dp2 = Math.max(dp0 + nums[i], dp1);
 */
public class Solution {

    public int rob(int[] nums) {
        int dp0 = 0;
        int dp1 = 0;
        for (int i = 0; i < nums.length; i++) {
            // 计算使用 nums[i] 的情况下的最大值
            int dp2 = Math.max(dp0 + nums[i], dp1);
            // 设置新的 dp0、dp1
            dp0 = dp1;
            dp1 = dp2;
        }
        return dp1;
    }

}
