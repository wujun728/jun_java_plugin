package cn.iocoder.algorithm.leetcode.no0055;

/**
 * 贪心算法
 *
 * 相比 {@link Solution} 来说，每一次循环，少一次计算，所以更快一些。所以，速度上会更快一些
 */
public class Solution02 {

    public boolean canJump(int[] nums) {
        int minPosition = nums.length - 1;
        for (int i = minPosition; i >= 0; i--) {
            if (nums[i] + i >= minPosition) {
                minPosition = i;
            }
        }
        return minPosition == 0;
    }

}
