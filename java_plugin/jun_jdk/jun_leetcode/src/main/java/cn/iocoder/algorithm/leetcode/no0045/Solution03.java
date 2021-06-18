package cn.iocoder.algorithm.leetcode.no0045;

/**
 * 数组
 *
 * 先站在终点 position = nums.length - 1;
 *
 * 不断从 [0, position) 的位置，走到 position 。
 */
public class Solution03 {

    public int jump(int[] nums) {
        int position = nums.length - 1;
        int step = 0;
        while (position > 0) { // 未到达终点
            // 从左到右，判断是否能到达 position 的最早位置。
            for (int i = 0; i < position; i++) {
                if (i + nums[i] >= position) {
                    step++;
                    position = i;
                    break;
                }
            }
        }
        return step;
    }

}
