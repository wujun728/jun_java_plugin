package cn.iocoder.algorithm.leetcode.no0494;

/**
 * 动态规划
 *
 * 比较巧妙，参考博客
 * 1. https://leetcode-cn.com/problems/target-sum/solution/0-1bei-bao-dong-tai-gui-hua-jie-da-bai-90de-ti-jia/
 * 2. https://github.com/CyC2018/CS-Notes/blob/master/notes/Leetcode%20%E9%A2%98%E8%A7%A3%20-%20%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92.md#2-%E6%94%B9%E5%8F%98%E4%B8%80%E7%BB%84%E6%95%B0%E7%9A%84%E6%AD%A3%E8%B4%9F%E5%8F%B7%E4%BD%BF%E5%BE%97%E5%AE%83%E4%BB%AC%E7%9A%84%E5%92%8C%E4%B8%BA%E4%B8%80%E7%BB%99%E5%AE%9A%E6%95%B0
 *
 * 选择 x 部分，取正；选择 y 部分，取负
 * 那么，sum(x) + sum(y) = sum ；sum(x) - sum(y) = target 。
 * 可得，2 * sum(x) = sum + target ；最终 sum(x) = (sum + target) / 2 。
 *
 * 这样，我们只需要去寻找，满足 x 能够达到 (sum + target) / 2 的组合，这样剩余的 y ，就都取负号即可。
 *
 * 真的是巧妙噢。
 *
 * 最终，表达式是，dp[i] = dp[i] + dp[i - nums[i]] 。
 *
 * TODO 芋艿，后续可以多看看。
 */
public class Solution02 {

    private static final int N = 1000;

    public int findTargetSumWays(int[] nums, int S) {
        if (S > N) { // 超过该值，说明肯定不会有组合
            return 0;
        }
        // 求和
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (((sum + S) & 1) == 1) { // 奇数，无法除 2.
            return 0;
        }
        int max = (sum + S) >> 1;

        // dp
        int[] dp = new int[max + 1];
        dp[0] = 1; // 0 的情况下，无需数字，所以组合是 1 。
        for (int num : nums) {
            for (int i = max; i >= num; i--) {
                dp[i] = dp[i] + dp[i - num];
            }
        }
        return dp[max];
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.findTargetSumWays(new int[]{1}, 1));
        System.out.println(solution.findTargetSumWays(new int[]{0}, 0));
    }

}
