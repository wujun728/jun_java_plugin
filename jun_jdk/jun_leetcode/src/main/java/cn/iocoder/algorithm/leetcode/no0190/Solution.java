package cn.iocoder.algorithm.leetcode.no0190;

/**
 * https://leetcode-cn.com/problems/reverse-bits/
 *
 * 位操作
 */
public class Solution {

    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n = n >> 1;
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        solution.reverseBits(43261596);
        System.out.println(solution.reverseBits(43261596));
    }

}
