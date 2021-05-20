package cn.iocoder.algorithm.leetcode.no0088;

/**
 * https://leetcode-cn.com/problems/merge-sorted-array/
 *
 * 因为 nums1 的大小，是 m + n ，所以空间是足够的
 * 又因为，从头开始比较会导致覆盖，所以从尾部开始比较。
 *
 * 当然，该方法，还有其他解决方案，都不如这个，嘿嘿。可以查看：https://leetcode-cn.com/problems/merge-sorted-array/solution/he-bing-liang-ge-you-xu-shu-zu-by-leetcode/
 */
public class Solution {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 开始比较
        int idx = nums1.length - 1;
        while (n > 0 && m > 0) {
            if (nums1[m - 1] > nums2[n - 1]) {
                nums1[idx] = nums1[m - 1];
                idx--;
                m--;
            } else {
                nums1[idx] = nums2[n - 1];
                idx--;
                n--;
            }
        }

        // 如果 nums2 还有剩余，复制到 nums1 中
        if (n > 0) {
            System.arraycopy(nums2, 0, nums1, 0, n);
        }
    }

}
