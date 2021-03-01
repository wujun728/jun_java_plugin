package cn.iocoder.algorithm.leetcode.no0080;

/**
 * 通用模板，可以将 k 变量，修改成任意一个有序数组中，允许的一个数字的数量
 *
 * 参考博客：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii/solution/shan-chu-pai-xu-shu-zu-zhong-de-zhong-fu-xiang-i-2/
 */
public class Solution02 {

    public int removeDuplicates(int[] nums) {
        int k = 2;
        if (nums.length <= k) {
            return nums.length;
        }

        int idx = k;
        for (int i = k; i < nums.length; i++) {
            // 位置 i ，直接和位置 idx - k 比较，而跳过了位置 idx - k + 1 ... 的元素。
            // 因为有序，只要 nums[i] == nums[idx - k] 成立，意味着 nums[i] == nums[idx - k + 1] 也成立。
            if (nums[i] == nums[idx - k]) {
                continue;
            }
            // 设置
            nums[idx] = nums[i];
            idx++;
        }

        return idx;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(solution.removeDuplicates(new int[]{1, 1, 1, 2, 2, 3}));
        System.out.println(solution.removeDuplicates(new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3}));
    }

}
