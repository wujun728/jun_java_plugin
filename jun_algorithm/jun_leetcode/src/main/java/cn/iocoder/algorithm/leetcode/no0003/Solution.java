package cn.iocoder.algorithm.leetcode.no0003;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 */
public class Solution {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // 初始化一堆遍历，默认使用第一个字符
        int begin = 0;
        int max = 1;
        Map<Character, Integer> characterIndexes = new HashMap<>();
        characterIndexes.put(s.charAt(0), 0);

        for (int i = 1; i < s.length(); i++) {
            Integer index = characterIndexes.get(s.charAt(i));
            // 已经存在
            if (index != null && index >= begin) {
                begin = index + 1;
            } else {
                max = Math.max(max, i - begin + 1);
            }
            characterIndexes.put(s.charAt(i), i);
        }

        return max;
    }

}
