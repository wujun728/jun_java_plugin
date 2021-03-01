package cn.iocoder.algorithm.leetcode.no0026;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/
 *
 * 从逻辑来说，貌似也算“双指针”，一个 i ，一个 idx 。。。
 */
public class Solution {

    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int idx = 1; // 从 1 开始，减少多余的每次的 i > 0 的判断
        for (int i = 1; i < nums.length; i++) {
            // 等于前一个元素，跳过
            if (nums[i] == nums[i - 1]) {
                continue;
            }
            nums[idx] = nums[i];
            idx++;
        }

        return idx;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}));
    }

}
