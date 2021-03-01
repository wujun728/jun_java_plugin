package cn.iocoder.algorithm.leetcode.no0342;

/**
 * https://leetcode-cn.com/problems/power-of-four/
 *
 * 位操作
 *
 * 1 =》00001
 * 4 =》00100
 * 8 =》10000
 */
public class Solution {

    public boolean isPowerOfFour(int num) {
        if (num <= 0) {
            return false;
        }
        // 如果是 4 进制，必须满足 2 进制。
        if ((num & (num - 1)) != 0) { // 注意，如果不判断
            return false;
        }
        // 奇数位置，都是 1 。
        if ((num & 0b01010101010101010101010101010101) == num) {
            return true;
        }
        return false;
    }

}
