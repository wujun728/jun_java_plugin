package cn.iocoder.algorithm.leetcode.no0035;

/**
 * https://leetcode-cn.com/problems/search-insert-position/
 */
public class Solution {

    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if (nums[middle] == target) {
                return middle;
            }
            if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        // 相比来说，返回 left ，表示最接近的位置
        return left;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.searchInsert(new int[]{1, 3, 5, 6}, 5));
//        System.out.println(solution.searchInsert(new int[]{1, 3, 5, 6}, 2));
//        System.out.println(solution.searchInsert(new int[]{1, 3, 5, 6}, 7));
        System.out.println(solution.searchInsert(new int[]{1, 3, 5, 6}, 0));
    }

}
