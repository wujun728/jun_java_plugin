package cn.iocoder.algorithm.leetcode.no0080;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii/
 *
 * 写的有点冗余，所以后来改写成 {@link Solution02}
 */
public class Solution {

    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) {
            return nums.length;
        }

        int idx = 2; // 省略下面的 i >= 2
        int last = nums[1]; // 最后一个值
        int lastCount = nums[0] == nums[1] ? 2 : 1; // 最后一个值出现的次数
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] == last) {
                // 如果最后一个值出现大于等于 2 次，说明重复过多，跳过
                if (lastCount >= 2) {
                    continue;
                }
                // 计数
                lastCount++;
            } else {
                // 重置
                last = nums[i];
                lastCount = 1;
            }
            // 设置
            nums[idx] = nums[i];
            idx++;
        }

        return idx;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.removeDuplicates(new int[]{1, 1, 1, 2, 2, 3}));
        System.out.println(solution.removeDuplicates(new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3}));
    }

}
