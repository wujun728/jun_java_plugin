package cn.iocoder.algorithm.leetcode.no0042;

/**
 * https://leetcode-cn.com/problems/trapping-rain-water/
 *
 * 1. 先求从右边到左的最大值
 * 2. 然后顺序遍历，一个格子能积多少水，是和其左边两边的最大值的较小者的差值。
 */
public class Solution {

    public int trap(int[] height) {
        if (height.length == 0) {
            return 0;
        }
        // 从右到左，求最大值。
        int[] rightMaxes = new int[height.length];
        int rightMax = Integer.MIN_VALUE;
        for (int i = height.length - 1; i >= 0; i--) {
            rightMax = Math.max(rightMax, height[i]);
            rightMaxes[i] = rightMax;
        }

        // 计算
        int sum = 0;
        int leftMax = height[0];
        for (int i = 1; i < height.length - 1; i++) {
            // 计算量
            int min = Math.min(leftMax, rightMaxes[i + 1]);
            if (height[i] < min) {
                sum += min - height[i];
            }
            // 设置新的最高高度
            leftMax = Math.max(leftMax, height[i]);
        }
        return sum;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        System.out.println(solution.trap(new int[]{2, 0, 2}));
    }

}
