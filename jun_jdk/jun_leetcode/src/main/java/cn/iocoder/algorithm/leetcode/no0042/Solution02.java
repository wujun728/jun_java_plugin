package cn.iocoder.algorithm.leetcode.no0042;

/**
 * 暴力解，每个节点，求左右两边的最大值
 */
public class Solution02 {

    public int trap(int[] height) {
        int sum = 0;
        for (int i = 1; i < height.length - 1; i++) {
            // 左边最大值
            int leftMax = height[i];
            for (int j = i - 1; j >= 0; j--) {
                leftMax = Math.max(leftMax, height[j]);
            }
            // 右边最大值
            int rightMax = height[i];
            for (int j = i + 1; j < height.length; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }

            // 增加结果
            sum += Math.min(leftMax, rightMax) - height[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(solution.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
//        System.out.println(solution.trap(new int[]{2, 0, 2}));
    }

}
