package cn.iocoder.algorithm.leetcode.no0377;

/**
 * http://leetcode-cn.com/problems/combination-sum-iv/
 *
 * 动态规划
 *
 * dp[i] = dp[i] + dp[i - num]
 *
 * 其中，i 为指定值。
 */
public class Solution {

    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1; // 0 的话，一定有一个组合
        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if (num > i) {
                    continue;
                }
                dp[i] = dp[i] + dp[i - num];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.combinationSum4(new int[]{1, 2, 3}, 4));
    }

}
