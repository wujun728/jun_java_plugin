package cn.iocoder.algorithm.leetcode.no0055;

/**
 * https://leetcode-cn.com/problems/jump-game/
 *
 * 数组
 *
 * 顺着从头晚跳，记录最远可跳位置。只要当前位置小于最远位置，说明可以达到。
 */
public class Solution {

    public boolean canJump(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            // 判断是否不可达
            if (i > max) {
                return false;
            }
            // 计算新的 max
            max = Math.max(max, i + nums[i]);
        }
        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.canJump(new int[]{2,3,1,1,4}));
        System.out.println(solution.canJump(new int[]{3,2,1,0,4}));
        System.out.println(solution.canJump(new int[]{0}));
    }

}
