package cn.iocoder.algorithm.leetcode.no0053;

/**
 * https://leetcode-cn.com/problems/maximum-subarray/
 *
 * 贪心算法
 *
 * 不断计算使用当前值的最大值，然后和全局最大值比较。
 */
public class Solution {

    public int maxSubArray(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int result = nums[0]; // 全局最大值
        int max = nums[0]; // 使用当前数字，能产生的最大值
        for (int i = 1; i < nums.length; i++) {
            // 计算使用当前值的最大值
            max = Math.max(max + nums[i], nums[i]);
            // 计算全局最大值
            result = Math.max(result, max);
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }

}
