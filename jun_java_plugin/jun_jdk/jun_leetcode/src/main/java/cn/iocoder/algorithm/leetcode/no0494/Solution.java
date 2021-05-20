package cn.iocoder.algorithm.leetcode.no0494;

/**
 * https://leetcode-cn.com/problems/target-sum/
 *
 * 动态规划
 *
 * dp[i] = dp[i - nums[i]] + dp[i + nums[i]]
 */
public class Solution {

    private static final int N = 1000;

    public int findTargetSumWays(int[] nums, int S) {
        if (nums.length == 0) {
            return S == 0 ? 1 : 0;
        }
        if (S > N) {
            return 0;
        }

        // 初始化 dp 数组
        int[] dp = new int[2 * N + 1]; // 要注意 + 1 ，因为和可能是 1000 。因为可能存在负数，所以 2 * N ，那么 0 实际对应的下标就是 N 。
        dp[N - nums[0]]++; // 这里要注意，因为初始可能是 0 ，-0 和 +0 是两个组合
        dp[N + nums[0]]++;
        // 开始 dp
        for (int i = 1; i < nums.length; i++) {
            int[] result = new int[dp.length];
            for (int j = 0; j < dp.length; j++) {
                if (j - nums[i] >= 0) {
                    result[j] += dp[j - nums[i]];
                }
                if (j + nums[i] < dp.length) {
                    result[j] += dp[j + nums[i]];
                }
            }
            System.arraycopy(result, 0, dp, 0, result.length);
        }
        return dp[S + N];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.findTargetSumWays(new int[]{1, 1, 1, 1, 1}, 3));
//        System.out.println(solution.findTargetSumWays(new int[]{1, 2, 3}, 0));
//        System.out.println(solution.findTargetSumWays(new int[]{1000}, 1000));
        System.out.println(solution.findTargetSumWays(new int[]{0,0,0,0,0,0,0,0,1}, 1));
    }

}
