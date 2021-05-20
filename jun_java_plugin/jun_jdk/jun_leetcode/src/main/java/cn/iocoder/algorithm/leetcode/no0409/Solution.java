package cn.iocoder.algorithm.leetcode.no0409;

/**
 * https://leetcode-cn.com/problems/longest-palindrome/
 */
public class Solution {

    public int longestPalindrome(String s) {
        // 计数
        int[] counts = new int['z' - 'A' + 1];
        for (char ch : s.toCharArray()) {
            counts[ch - 'A']++;
        }

        // 计算最长
        int longest = 0;
        boolean extra = false;
        for (int count : counts) {
            longest += count / 2;
            if (!extra && (count & 1) == 1) {
                extra = true;
            }
        }

        return longest * 2 + (extra ? 1 : 0);
    }

}
