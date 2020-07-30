package cn.iocoder.algorithm.leetcode.no0404;

/**
 * https://leetcode-cn.com/problems/partition-equal-subset-sum
 *
 * 动态规划
 *
 * 1. 先求和总数
 * 2. 然后 dp[i] = true, 如果 dp[i - nums[i]] = true 。
 */
public class Solution {

    public boolean canPartition(int[] nums) {
        // 求和
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) == 1) {
            return false;
        }

        // 开始 dp
        boolean[] dp = new boolean[sum / 2 + 1];
        dp[0] = true;
        for (int num : nums) {
//            for (int i = 0; i < dp.length && i + num < dp.length; i++) {
//                if (dp[i]) {
//                    dp[i + num] = true;
//                }
//            }
            for (int i = dp.length - 1; i >= num; i--) { // 倒序
                if (dp[i - num]) {
                    dp[i] = true;
                }
            }
            if (dp[dp.length - 1]) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.canPartition(new int[]{1, 2, 5}));
        System.out.println(solution.canPartition(new int[]{1, 5, 11, 5}));
        System.out.println(solution.canPartition(new int[]{1, 2, 3, 5}));
    }

}
