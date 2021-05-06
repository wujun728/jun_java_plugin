package cn.iocoder.algorithm.leetcode.no0287;

/**
 * https://leetcode-cn.com/problems/find-the-duplicate-number/
 *
 * 暴力法
 *
 * 时间复杂度是 O(N^2)
 */
public class Solution01 {

    public int findDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return nums[i];
                }
            }
        }

        // 找不到符合的
        return -1;
    }

    public static void main(String[] args) {
        Solution01 solution = new Solution01();
        System.out.println(solution.findDuplicate(new int[]{1 ,3, 4, 2, 2}));
        System.out.println(solution.findDuplicate(new int[]{3 ,1, 3, 4, 2}));
    }

}
