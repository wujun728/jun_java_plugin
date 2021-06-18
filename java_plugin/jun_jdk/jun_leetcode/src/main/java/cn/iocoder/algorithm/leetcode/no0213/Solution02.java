package cn.iocoder.algorithm.leetcode.no0213;

/**
 * 动态规划
 *
 * 两次动态规划，然后对比，取最大值。
 * 第一轮，去掉头元素
 * 第二轮，去掉尾元素
 *
 * 每一轮的计算方式，和 {@link cn.iocoder.algorithm.leetcode.no0198.Solution} 一样。
 */
public class Solution02 {

    public int rob(int[] nums) {
        if (nums.length == 1) { // 只有 1 个元素的情况下，如果去头去尾，会没结果。
            return nums[0];
        }
        int result1 = this.rob(nums, 1, nums.length); // 去偷
        int result2 = this.rob(nums, 0, nums.length - 1); // 去尾
        return Math.max(result1, result2);
    }

    private int rob(int[] nums, int start, int end) {
        int dp0 = 0;
        int dp1 = 0;
        for (int i = start; i < end; i++) {
            int dp2 = Math.max(dp0 + nums[i], dp1);
            // 赋值
            dp0 = dp1;
            dp1 = dp2;
        }
        return dp1;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.rob(new int[]{2, 3, 2}));
//        System.out.println(solution.rob(new int[]{1, 2, 3, 1}));
//        System.out.println(solution.rob(new int[]{2, 1, 1, 2}));
//        System.out.println(solution.rob(new int[]{2, 1, 4}));
        System.out.println(solution.rob(new int[]{1}));
    }

}
