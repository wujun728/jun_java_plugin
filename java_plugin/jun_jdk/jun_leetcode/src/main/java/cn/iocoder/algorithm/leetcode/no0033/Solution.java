package cn.iocoder.algorithm.leetcode.no0033;

/**
 * https://leetcode-cn.com/problems/search-in-rotated-sorted-array/
 *
 * 本题 nums 不包括重复的
 */
public class Solution {

    private int findMinNumberIndex(int[] nums) {
        // 全部递增。例如说，1、2、3、4、5、6
        if (nums[0] < nums[nums.length - 1]) {
            return 0;
        }

        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int middle = low + ((high - low) >> 1);
            if (nums[middle] > nums[middle + 1]) { // 找到了指定点
                return middle + 1;
            } else if (nums[middle] < nums[low]) { // 中间比结尾小，说明还在递增区域，所以 high 减小
                high = middle - 1;
            } else { // 中间比结尾大，说明在两端，所以 low 增加
                low = middle + 1;
            }
        }

        throw new IllegalArgumentException("不会出现该情况！");
    }

    private int binarySearch(int[] nums, int low, int high, int target) {
        while (low <= high) {
            int middle = low + ((high - low) >> 1);
            if (nums[middle] == target) {
                return middle;
            }
            if (target < nums[middle]) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }

        return -1;
    }

    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0] == target ? 0 : -1;
        }

        // 寻找最小数字的位置
        int minNumberIndex = this.findMinNumberIndex(nums);

        // 如果当前值比最小值还小，返回不存在
        if (nums[minNumberIndex] > target) {
            return -1;
        }

        // 恰好等于最小值，直接返回
        if (nums[minNumberIndex] == target) {
            return minNumberIndex;
        }

        // 剩余的情况，target 就大于 nums[minNumberIndex] ，那么就需要判断两边的情况。

        if (target <= nums[nums.length - 1]) { // 右半区间
            return this.binarySearch(nums, minNumberIndex + 1, nums.length - 1, target);
        } else { // 右半区间
            return this.binarySearch(nums, 0, minNumberIndex - 1, target);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 4));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 5));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 6));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 7));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 8));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 9));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 0));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 1));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 2));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 3));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, -1));
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 10));
        System.out.println(solution.search(new int[]{10}, 9));
        System.out.println(solution.search(new int[]{1, 3}, 1));
        System.out.println(solution.search(new int[]{1, 3}, 3));
        System.out.println(solution.search(new int[]{1, 3}, 10));
        System.out.println(solution.search(new int[]{1, 3, 5}, 1));
        System.out.println(solution.search(new int[]{4, 5, 1, 2, 3}, 1));
    }

}
