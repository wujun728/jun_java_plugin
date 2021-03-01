package cn.iocoder.algorithm.leetcode.no0213;

/**
 * https://leetcode-cn.com/problems/house-robber-ii/
 *
 * 动态规划
 *
 * 原先思路，是想通过增加 flag 状态位，表示是否使用了第 0 位。结果发现不行，例如说 [2, 1, 4] 这个用例，直接不使用 4 ，导致答案错误。
 */
@Deprecated // 不通过
public class Solution {

    public int rob(int[] nums) {
        int dp0 = 0;
        int dp1 = 0;
        boolean dp0Flag = false;
        boolean dp1Flag = false;
        for (int i = 0; i < nums.length; i++) {
            if (i < nums.length - 1) {
                int dp2 = Math.max(dp0 + nums[i], dp1);
                boolean dp2Flag = false;
                if (i == 0 && dp0 + nums[i] > dp1) {
                    dp2Flag = true;
                }
                // 赋值
                dp0 = dp1;
                dp0Flag = dp1Flag;
                dp1 = dp2;
                dp1Flag = dp2Flag;
            } else { // 最后一个
                if (!dp0Flag) {
                    dp0 = dp0 + nums[i];
                }
            }
            System.out.println();
        }
        return Math.max(dp0, dp1);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.rob(new int[]{2, 3, 2}));
//        System.out.println(solution.rob(new int[]{1, 2, 3, 1}));
//        System.out.println(solution.rob(new int[]{2, 1, 1, 2}));
        System.out.println(solution.rob(new int[]{2, 1, 4}));
//        System.out.println(solution.rob(new int[]{1}));
    }

}
