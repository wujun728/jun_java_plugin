package cn.iocoder.algorithm.leetcode.no0153;

/**
 * https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/
 *
 * 参考博客：https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/solution/xun-zhao-xuan-zhuan-pai-lie-shu-zu-zhong-de-zui-xi/
 */
public class Solution {

    public int findMin(int[] nums) {
        if (nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0];
        }

        // 判断是否是递增的
        if (nums[0] < nums[nums.length - 1]) {
            return nums[0];
        }

        // 变形的二分查找
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (nums[mid] > nums[mid + 1]) {
                return nums[mid + 1];
            }
            if (nums[low] > nums[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        throw new IllegalArgumentException("不存在该情况！");
    }

}
