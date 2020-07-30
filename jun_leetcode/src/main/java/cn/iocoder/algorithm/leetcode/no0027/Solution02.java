package cn.iocoder.algorithm.leetcode.no0027;

/**
 * 相比 {@link Solution} 来说，优势在于，Solution02 可以减少移动的次数
 */
public class Solution02 {

    public int removeElement(int[] nums, int val) {
        int idx = 0;
        int n = nums.length;
        while (idx < n) {
            if (nums[idx] == val) {
                nums[idx] = nums[n - 1];
                n--; // 此处，非常关键，减小的是 n ，而 idx 没有增加，那么 while 下一轮，就会判断此处的 nums[n - 1] 是否也有可能等于 val
            } else {
                idx++;
            }
        }

        return n;
    }

}
