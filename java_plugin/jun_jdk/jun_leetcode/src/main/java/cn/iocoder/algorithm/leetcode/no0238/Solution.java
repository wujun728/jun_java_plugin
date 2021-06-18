package cn.iocoder.algorithm.leetcode.no0238;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/product-of-array-except-self/
 *
 * 思路，当前数字的结果，等于前面的数字的乘积 * 后面的数字的乘积
 */
public class Solution {

    public int[] productExceptSelf(int[] nums) {
        // 从左到右，计算截止
        int k = 1;
        int[] output = new int[nums.length]; // 先存储，左边的乘积结果
        for (int i = 0; i < nums.length; i++) {
            output[i] = k;
            k = k * nums[i];
        }

        // 从右到左
        k = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            output[i] = output[i] * k; // 在 * 右边的结果
            k = k * nums[i];
        }

        return output;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.productExceptSelf(new int[]{1, 2, 3, 4})));
    }

}
