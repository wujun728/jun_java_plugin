package cn.iocoder.algorithm.leetcode.no0287;

/**
 * 二分查找法
 *
 * 时间复杂度是 O(N logN) ，计算方式是，二分是 0(logN) ，然后每个二分，遍历数组，是 N ，所以相乘的结果就是 O(N logN) 。
 *
 * 参考博客：https://leetcode-cn.com/problems/find-the-duplicate-number/solution/er-fen-fa-si-lu-ji-dai-ma-python-by-liweiwei1419/
 */
public class Solution03 {

    public int findDuplicate(int[] nums) {
        int left = 1;
        int right = nums.length;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            // 计算小于 mid 的数值
            int counts = 0;
            for (int num : nums) {
                if (num <= mid) {
                    counts++;
                }
            }
            // 计算折半
            if (counts > mid) { // 超过 mid ，说明重复在左半区，缩小
                right = mid - 1;
            } else { // <= mid ，说明重复在右半区，放大
                left = mid + 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
        System.out.println(solution.findDuplicate(new int[]{1 ,2, 2, 4}));
    }

}
