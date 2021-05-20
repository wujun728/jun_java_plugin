package cn.iocoder.algorithm.leetcode.no0290;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * https://leetcode-cn.com/problems/word-pattern/
 */
public class Solution {

    public boolean wordPattern(String pattern, String str) {
        if (pattern == null || str == null) {
            return false;
        }

        char[] patternArray = pattern.toCharArray();
        String[] strArray = str.split(" ");
        if (patternArray.length != strArray.length) {
            return false;
        }

        Map<Character, Integer> patternIndexes = new HashMap<>();
        Map<String, Integer> strIndexes = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            Integer patternIndex = patternIndexes.get(patternArray[i]);
            Integer strIndex = strIndexes.get(strArray[i]);
            if (!Objects.equals(patternIndex, strIndex)) {
                return false;
            }
            patternIndexes.put(patternArray[i], i);
            strIndexes.put(strArray[i], i);
        }

        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.wordPattern("abba", "dog cat cat dog"));
    }

}
