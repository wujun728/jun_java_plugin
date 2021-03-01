package cn.iocoder.algorithm.leetcode.no0162;

public class Solution02 {

    public int findPeakElement(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1])
                return i;
        }

        // 如果都没符合，说明末尾就是尾巴
        return nums.length - 1;
    }

}
