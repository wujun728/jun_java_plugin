package cn.iocoder.algorithm.leetcode.no0055;

/**
 * 动态规划
 *
 * 自顶向上，判断当前节点开始 + 1 到 nums[i] 个节点，是否可以访问到。
 */
public class Solution03 {

    public boolean canJump(int[] nums) {
        // 创建状态数组，记录该节点是否可以访问。
        boolean[] dps = new boolean[nums.length];
        dps[nums.length - 1] = true; // 最后一个节点可访问

        // 倒序遍历
        for (int i = nums.length - 2; i >= 0; i--) {
            dps[i] = this.canVisit(dps,
                    i + 1,
                    Math.min(i + nums[i], nums.length - 1)); // 结尾
        }
        return dps[0];
    }

    private boolean canVisit(boolean[] dps, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (dps[i]) {
                return true;
            }
        }
        return false;
    }

}
