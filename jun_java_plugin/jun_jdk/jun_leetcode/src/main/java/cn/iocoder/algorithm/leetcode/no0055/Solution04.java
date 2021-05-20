package cn.iocoder.algorithm.leetcode.no0055;

/**
 * 动态规划，消除重复的回溯。
 *
 * 自上向下，参考 https://leetcode-cn.com/problems/jump-game/solution/tiao-yue-you-xi-by-leetcode/ 方法二。
 * 相比 {@link Solution03} 来说，会绕一点
 *
 * TODO 官方方法，竟然超时。
 */
public class Solution04 {

    public static final int FLAG_UNKNOWN = 0;
    public static final int FLAG_SUCCESS = 1;
    public static final int FLAG_FAILURE = 2;

    private boolean canJumpFromPosition(int position, int[] nums, int[] dps) {
        // 判断当前位置，是不是已经可以访问
        if (dps[position] != FLAG_UNKNOWN) {
            return dps[position] == FLAG_SUCCESS;
        }

        int furthestJump = Math.min(position + nums[position], nums.length - 1);
        for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {
            if (this.canJumpFromPosition(nextPosition, nums, dps)) {
                dps[nextPosition] = FLAG_SUCCESS;
                return true;
            }
        }

        dps[position] = FLAG_FAILURE;
        return false;
    }

    public boolean canJump(int[] nums) {
        // 创建状态数组
        int[] dps = new int[nums.length];
        dps[nums.length - 1] = FLAG_SUCCESS; // 标记可访问到
        return this.canJumpFromPosition(0, nums, dps);
    }

}
