package cn.iocoder.algorithm.leetcode.no0438;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/
 *
 * 滑动窗口
 */
public class Solution {

    public List<Integer> findAnagrams(String s, String p) {
        if (p.length() == 0 || s.length() == 0) {
            return Collections.emptyList();
        }
        // 计数
        int needCount = p.length();
        int[] ps = new int[26];
        for (int i = 0; i < p.length(); i++) {
            ps[p.charAt(i) - 'a']++;
        }

        // 遍历
        List<Integer> array = new ArrayList<>();
        int[] ss = new int[26];
        int matchCount = 0;
        for (int i = 0; i < s.length(); i++) {
            // 移除滑出的字符
            if (i - p.length() >= 0) {
                int chIndex = s.charAt(i - p.length()) - 'a';
                if (ps[chIndex] > 0) { // 是需要找的字符
                    ss[chIndex]--;
                    if (ss[chIndex] < ps[chIndex]) {
                        matchCount--;
                    }
                }
            }
            // 添加当前的字符
            int chIndex = s.charAt(i) - 'a';
            if (ps[chIndex] > 0) { // 是需要找的字符
                ss[chIndex]++;
                if (ss[chIndex] <= ps[chIndex]) {
                    matchCount++;
                }
            }
            // 判断是否符合
            if (matchCount == needCount) {
                array.add(i - p.length() + 1);
            }
        }
        return array;
    }

    // 0, 1, 2
    // 0

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.findAnagrams("cbaebabacd", "abc"));
        System.out.println(solution.findAnagrams("abab", "ab"));
    }

}
