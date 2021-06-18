package cn.iocoder.algorithm.leetcode.no0344;

/**
 * https://leetcode-cn.com/problems/reverse-string/
 */
public class Solution {

    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            // swap
            char tmp = s[left];
            s[left] = s[right];
            s[right] = tmp;
            // 互相靠近
            left++;
            right--;
        }
    }

}
