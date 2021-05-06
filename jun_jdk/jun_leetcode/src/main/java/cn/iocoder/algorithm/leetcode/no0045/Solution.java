package cn.iocoder.algorithm.leetcode.no0045;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/jump-game-ii/
 *
 * 动态规划
 *
 * 思路是，遍历每个节点，然后跳转到其后面的节点，计算 dps[j] = Math.max(dps[j, dps[i] + 1) 。
 *
 * 结果，超时。
 */
public class Solution {

    private static final int VISIT_NONE = -1;

    public int jump(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        // 初始化 dps 数组
        int[] dps = new int[nums.length];
        Arrays.fill(dps, 1, dps.length, VISIT_NONE);
        // 遍历每个节点
        for (int i = 0; i < nums.length; i++) {
            if (dps[i] == VISIT_NONE) {
                continue;
            }
            int maxIndex = Math.min(i + nums[i], nums.length - 1);
            for (int j = i + 1; j <= maxIndex; j++) {
                dps[j] = this.max(dps[j], dps[i] + 1);
            }
        }
        return dps[nums.length - 1];
    }

    private int max(int a, int b) {
        if (a == VISIT_NONE) {
            return b;
        }
        if (b == VISIT_NONE) {
            return a;
        }
        return Math.min(a, b);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.jump(new int[]{2, 3, 1, 1, 4}));
    }

}
