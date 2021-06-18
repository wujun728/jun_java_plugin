package cn.iocoder.algorithm.leetcode.no0189;

/**
 * https://leetcode-cn.com/problems/rotate-array/
 *
 * 使用反转，有点巧妙。
 * 参考 https://leetcode-cn.com/problems/rotate-array/solution/xuan-zhuan-shu-zu-by-leetcode/ 方法四
 */
public class Solution {

    public void rotate(int[] nums, int k) {
        // 先去掉多余的移动次数
        k = k % nums.length;
        // 整个反转
        this.swap(nums, 0, nums.length - 1);
        // 反转前 k 个
        this.swap(nums, 0, k - 1);
        // 反转后 k - n 个
        this.swap(nums, k, nums.length - 1);
    }

    private void swap(int[] nums, int start, int end) {
        while (start < end) {
            // 交换
            int tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
            // 前后修改
            start++;
            end--;
        }
    }

}
