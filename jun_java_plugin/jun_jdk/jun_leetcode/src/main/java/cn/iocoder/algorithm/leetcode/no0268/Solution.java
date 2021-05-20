package cn.iocoder.algorithm.leetcode.no0268;

/**
 * https://leetcode-cn.com/problems/missing-number/
 *
 * 位操作
 *
 * 异或操作，再拉一波
 */
public class Solution {

    public int missingNumber(int[] nums) {
        int n = nums.length;
        // 先顺序来一套 ^ 操作
        int result = 0;
        for (int i = 0; i <= n; i++) {
            result = result ^ i;
        }
        // 在把 nums 数组，顺序再来一套 ^ 操作
        for (int num : nums) {
            result = result ^ num;
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.missingNumber(new int[]{3, 0, 1}));
        System.out.println(solution.missingNumber(new int[]{9,6,4,2,3,5,7,0,1}));
    }

}
