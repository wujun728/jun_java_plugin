package cn.iocoder.algorithm.leetcode.no0476;

/**
 * https://leetcode-cn.com/problems/number-complement/
 *
 * 位操作
 *
 * 逐个位置，通过 ^ 操作取反，并移到对应位置。
 */
public class Solution {

    public int findComplement(int num) {
        int result = 0;
        int index = 0;
        while (num > 0) {
            // 取 num 当前最后一位，取反，并移动对应位置
            result = (((num & 1) ^ 1) << index) | result;
            // 去掉 number 最后一位
            num = num >> 1;
            // 增加位置
            index++;
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findComplement(2));
        System.out.println(solution.findComplement(5));
        System.out.println(solution.findComplement(1));
        System.out.println(solution.findComplement(0));
    }

}
