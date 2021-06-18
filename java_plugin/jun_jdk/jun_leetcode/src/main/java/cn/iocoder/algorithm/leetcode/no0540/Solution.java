package cn.iocoder.algorithm.leetcode.no0540;

/**
 * https://leetcode-cn.com/problems/single-element-in-a-sorted-array/
 *
 * 二分查找。
 *
 * 因为只有一个元素不成对，所以折半合适。
 * 这个代码，思路没问题，就是比较冗余，所以在 {@link Solution02} 简化
 */
public class Solution {

    public int singleNonDuplicate(int[] nums) {
        // 只有一个元素
        if (nums.length == 1) {
            return nums[0];
        }
        // 处理掉出现头部的情况，简化代码
        if (nums[0] != nums[1]) {
            return nums[0];
        }
        // 处理掉出现尾部的情况，简化代码
        if (nums[nums.length -1] != nums[nums.length - 2]) {
            return nums[nums.length - 1];
        }

        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if ((mid & 1) == 0) { // 偶数
                if (nums[mid] == nums[mid + 1]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else { // 奇数
                if (nums[mid] == nums[mid - 1]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return nums[left];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.singleNonDuplicate(new int[]{1, 1, 2, 3, 3, 4, 4, 8, 8}));
        System.out.println(solution.singleNonDuplicate(new int[]{3, 3, 7, 7, 10, 11, 11}));
    }

}
