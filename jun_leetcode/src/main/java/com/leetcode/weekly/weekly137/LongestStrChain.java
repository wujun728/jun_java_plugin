package com.leetcode.weekly.weekly137;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * 最长字符串链
 *
 * @author: BaoZhou
 * @date : 2019/5/19 11:31
 */
public class LongestStrChain {
    @Test
    public void test() {
        String s[] = {"ksqvsyq","ks","kss","czvh","zczpzvdhx","zczpzvh","zczpzvhx","zcpzvh","zczvh","gr","grukmj","ksqvsq","gruj","kssq","ksqsq","grukkmj","grukj","zczpzfvdhx","gru"};
        System.out.println(longestStrChain(s));
    }

    public int longestStrChain(String[] words) {

        int max = 1;
        boolean[] flags = new boolean[words.length];
        for (int i = words.length - 1; i >= 0; i--) {
            int result = 1;
            if (flags[i]) {
                continue;
            }
            if (words[i].length() <= max) {
                continue;
            }
            int t = i;
            for (int j = i - 1; j >= 0; j--) {
                if (check(words[j], words[t])) {
                    flags[j] = true;
                    t = j;
                    result++;
                } else {
                    continue;
                }
            }
            max = Math.max(result, max);
        }
        return max;
    }

    boolean check(String s1, String s2) {
        if (s2.length() != s1.length() + 1) {
            return false;
        }

        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();

        boolean hasAppeared = false;
        for (int i = 0; i < chars1.length; i++) {
            if (chars1[i] == chars2[i + (hasAppeared ? 1 : 0)]) {
                continue;
            } else {
                if (hasAppeared) {
                    return false;
                } else {
                    hasAppeared = true;
                }
            }
        }
        return true;
    }
}
