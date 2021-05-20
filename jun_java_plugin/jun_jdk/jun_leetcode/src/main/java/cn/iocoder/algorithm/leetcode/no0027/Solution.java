package cn.iocoder.algorithm.leetcode.no0027;

/**
 * https://leetcode-cn.com/problems/remove-element/
 */
public class Solution {

    public int removeElement(int[] nums, int val) {
        int idx = 0;
        for (int num : nums) {
            if (num == val) {
                continue;
            }
            nums[idx] = num;
            idx++;
        }
        return idx;
    }

}
