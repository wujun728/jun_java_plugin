package cn.iocoder.algorithm.leetcode.no0075;

/**
 * https://leetcode-cn.com/problems/sort-colors/
 *
 * 双指针
 */
public class Solution {

    public void sortColors(int[] nums) {
        int zero = -1; // 指向最后一个 0
        int two = nums.length; // 指向最后一个 2
        int one = 0;

        while(one < two) {
            int num = nums[one];
            if (num == 0) { // 指向 0 的情况
                swap(nums, zero + 1, one);
                zero++; // 指向最后一个 0
                one++; // 跳到下一个
            } else if (num == 2) {
                swap(nums, two - 1, one);
                two--; // 指向最后一个 2
                // 注意，此处不会将 one++ ，因为要考虑，交换过来的数字的情况，到底是 0，还是 1 。
            } else {
                one++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}
