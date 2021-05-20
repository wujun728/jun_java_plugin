package cn.iocoder.algorithm.leetcode.no0009;

/**
 * https://leetcode-cn.com/problems/palindrome-number/
 *
 * 这个是一个比较巧妙的解法。
 *
 * 当然，也可以通过字符串来求解。
 */
public class Solution {

    public boolean isPalindrome(int x) {
        // 负数，天然非回文
        if (x < 0) {
            return false;
        }
        // 0 ，倜然是回文
        if (x == 0) {
            return true;
        }
        // 以 0 结尾，肯定不是回文。
        if (x % 10 == 0) {
            return false;
        }

        int y = 0;
        // 当原始数字 x 小于反转后的数字 y 时，就意味着我们已经处理了一半位数的数字。
        while (x > y) {
            y = y * 10 + x % 10;
            x = x / 10;
        }

        return y == x // 位数为偶数
                || x == y / 10; // 位数为奇数，y 会比 x 多一位
    }

}
