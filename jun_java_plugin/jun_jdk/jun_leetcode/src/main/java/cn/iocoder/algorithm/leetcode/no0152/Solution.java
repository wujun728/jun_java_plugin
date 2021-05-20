package cn.iocoder.algorithm.leetcode.no0152;

/**
 * https://leetcode-cn.com/problems/maximum-product-subarray/
 *
 * 动态规划
 *
 * dp0 存储连续的最小值
 * dp1 存储连续的最大值
 * 然后，dp0 和 dp1 都乘以 nums[i] ，求值。
 * 之后，dp0 取上面的最大值，dp1 取上面的最小值。
 */
public class Solution {

    public int maxProduct(int[] nums) {
        assert nums.length > 0; // 测试用例，保证有数字

        int max = nums[0];
        int dp0 = nums[0];
        int dp1 = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 乘以 nums[i] ，然后求新的 dp0、dp1
            dp0 = dp0 * nums[i];
            dp1 = dp1 * nums[i];
            int newDp0 = this.max(dp0, dp1, nums[i]);
            int newDp1 = this.min(dp0, dp1, nums[i]);
            // 计算最大值
            max = Math.max(newDp0, max);
            // 赋值
            dp0 = newDp0;
            dp1 = newDp1;
        }
        return max;
    }

    private int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    private int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxProduct(new int[]{2, 3, -2, 4}));
        System.out.println(solution.maxProduct(new int[]{-2, 0, -1}));
        System.out.println(solution.maxProduct(new int[]{-2, 3, -4}));
    }

}
