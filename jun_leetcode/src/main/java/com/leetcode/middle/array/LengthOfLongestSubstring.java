package com.leetcode.middle.array;

/**
 * 无重复字符的最长子串
 *
 * @author BaoZhou
 * @date 2018/12/23
 */
public class LengthOfLongestSubstring {
    public static void main(String[] args) {
        //System.out.println(lengthOfLongestSubstring("abcabcbb"));
        //System.out.println(lengthOfLongestSubstring("bbbbb"));
        //System.out.println(lengthOfLongestSubstring("pwwkew"));
        //System.out.println(lengthOfLongestSubstring(" "));
        //System.out.println(lengthOfLongestSubstring(""));
        //System.out.println(lengthOfLongestSubstring("au"));
        //System.out.println(lengthOfLongestSubstring("dvdf"));
        System.out.println(lengthOfLongestSubstring("aabaab!bb"));
    }

    public static int lengthOfLongestSubstring(String s) {
        int[] m = new int[256];
        int res = 0, left = 0;
        for (int i = 0; i < s.length(); i++) {
            left = Math.max(left, m[s.charAt(i)]);
            res = Math.max(res, i - left + 1);
            m[s.charAt(i)] = i + 1;
        }
        return res;
    }
}
