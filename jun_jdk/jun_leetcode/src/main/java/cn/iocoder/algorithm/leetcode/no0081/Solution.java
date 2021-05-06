package cn.iocoder.algorithm.leetcode.no0081;

/**
 * https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/
 *
 * 本题 nums 包括重复的
 */
public class Solution {

    private int findMinNumberIndex(int[] nums, int length) {
        // 默认递增，所以首个就是最小值。例如说 nums = {1, 2, 3, 4, 5}
        if (nums[0] < nums[length - 1]) {
            return 0;
        }

        // 例如说，nums = {3, 4, 1, 2}
        int low = 0, high = length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 转折点，即前一个大于后一个
            if (nums[mid] > nums[mid + 1]) {
                return mid + 1;
            }
            // 还是递增，所以 high 减小
            if (nums[low] > nums[mid]) { // 注意，此处没有等号
//                low = mid + 1;
                high = mid - 1;
            } else {
//                high = mid - 1;
                low = mid + 1;
            }
        }

        throw new IllegalArgumentException("不会出现这个情况");
    }

    private int binarySearch(int[] nums, int low, int high, int target) {
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] >= target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    public boolean search(int[] nums, int target) {
        // 数组为空，直接返回 false
        if (nums == null || nums.length == 0) {
            return false;
        }
        // 只有一个元素的情况下
        if (nums.length == 1) {
            return nums[0] == target;
        }

        // 剔除尾巴等于头的情况
        int length = nums.length;
        while (length > 1 && nums[length - 1] == nums[0]) {
            length--;
        }
        if (length == 1) {
            return nums[0] == target;
        }

        // 寻找最小值的下标
        int minNumberIndex = this.findMinNumberIndex(nums, length);

        // 如果最小值，大于 target
        if (nums[minNumberIndex] > target) {
            return false;
        }

        // 如果最小值，等于 target
        if (nums[minNumberIndex] == target) {
            return true;
        }

        // 如果最小值，大于 target ，分别在左右两边
        if (target <= nums[length - 1]) {
            return this.binarySearch(nums, minNumberIndex + 1, length - 1, target) >= 0;
        } else {
            return this.binarySearch(nums, 0, minNumberIndex - 1, target) >= 0;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 4));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 5));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 6));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 7));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 8));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 9));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 0));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 1));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 2));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 3));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, -1));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 8, 9, 0, 1, 2, 3}, 10));
//        System.out.println(solution.search(new int[]{10}, 9));
//        System.out.println(solution.search(new int[]{1, 3}, 1));
//        System.out.println(solution.search(new int[]{1, 3}, 3));
//        System.out.println(solution.search(new int[]{1, 3}, 10));
//        System.out.println(solution.search(new int[]{1, 3, 5}, 1));
//        System.out.println(solution.search(new int[]{4, 5, 1, 2, 3}, 1));
//        System.out.println(solution.search(new int[]{1, 1}, 0));
//        System.out.println(solution.search(new int[]{1, 1}, 1));
//        System.out.println(solution.search(new int[]{2, 5, 6, 0, 0, 1, 2}, 0));
        System.out.println(solution.search(new int[]{2, 2, 2, 0, 2, 2}, 0));
//        System.out.println(solution.search(new int[]{2, 2, 2, 2, 0, 0, 0, 2, 2}, 0));
    }

}
