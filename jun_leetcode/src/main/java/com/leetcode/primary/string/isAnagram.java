package com.leetcode.primary.string;

/**
 * 有效的字母异位词
 *
 * @author BaoZhou
 * @date 2018/12/10
 */
public class isAnagram {

    public static void main(String[] args) {
        String s = "anagram", t = "nagaram";
        System.out.println(isAnagram(s, t));
    }

    public static boolean isAnagram(String s, String t) {
        char ss[] = s.toCharArray();
        char tt[] = t.toCharArray();
        if (ss.length != tt.length) {
            return false;
        }
        int[] words = new int[26];
        for (int i = 0; i < ss.length; i++) {
            words[ss[i] - 'a']++;
        }
        for (int i = 0; i < tt.length; i++) {
            if (--words[tt[i] - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
