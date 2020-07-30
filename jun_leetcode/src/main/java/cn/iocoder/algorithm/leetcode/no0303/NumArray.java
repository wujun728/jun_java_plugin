package cn.iocoder.algorithm.leetcode.no0303;

/**
 * https://leetcode-cn.com/problems/range-sum-query-immutable/
 *
 * 数组
 */
public class NumArray {

    private int[] nums;

    public NumArray(int[] nums) {
        this.nums = nums;
    }

    public int sumRange(int i, int j) {
        int sum = 0;
        for (int k = i; k <= j; k++) {
            sum += nums[k];
        }
        return sum;
    }

}
