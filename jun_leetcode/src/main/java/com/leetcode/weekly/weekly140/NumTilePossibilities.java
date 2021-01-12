package com.leetcode.weekly.weekly140;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 活字印刷
 *
 * @author: BaoZhou
 * @date : 2019/6/9 10:32
 */
public class NumTilePossibilities {
    @Test
    public void test() {
        String test = "AAABBC";
        System.out.println(numTilePossibilities(test));
    }

    int[] p = new int[26];
    int ans = 0;

    public int numTilePossibilities(String tiles) {
        for (int i = 0; i < tiles.length(); i++) {
            p[(int) tiles.charAt(i) - (int) 'A']++;
        }
        try1(0);
        return ans;
    }

    private void try1(int n) {
        for (int i = 0; i < 26; i++) {
            if (p[i] > 0) {
                ans++;
                p[i] = p[i] - 1;
                try1(n + 1);
                p[i] = p[i] + 1;
            }
        }
    }
}
