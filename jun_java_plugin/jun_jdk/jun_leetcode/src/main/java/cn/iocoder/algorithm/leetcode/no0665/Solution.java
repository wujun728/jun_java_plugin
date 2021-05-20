package cn.iocoder.algorithm.leetcode.no0665;

/**
 * https://leetcode-cn.com/problems/non-decreasing-array/
 *
 * 数组
 */
public class Solution {

    public boolean checkPossibility(int[] nums) {
        if (nums.length <= 1) {
            return true;
        }
        int n = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] <= nums[i]) {
                continue;
            }
            n--;
            if (n < 0) {
                return false;
            }
            // 方案一，把当前数字，变成 i + 1 的数字
            if (i == nums.length - 1) { // 结尾，直接不用变，肯定符合。
                return true;
            }
            if (nums[i + 1] < nums[i - 1]) { // 方案一，失败
                if (i == 1) { // 方案二，如果 i 是第 1 位，则直接尝试将 nums[i - 1] 变成 nums[i] ，无需向第 -1 位比较
                    continue;
                }
                if (nums[i] < nums[i - 2]) { // 方案二，失败
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.checkPossibility(new int[]{4, 2, 3}));
        System.out.println(solution.checkPossibility(new int[]{4, 2, 1}));
        System.out.println(solution.checkPossibility(new int[]{2, 2, 1, 2}));
        System.out.println(solution.checkPossibility(new int[]{2, 1, 2}));
        System.out.println(solution.checkPossibility(new int[]{2, 3, 3, 2, 4}));
    }

    // 2 2 3

}
