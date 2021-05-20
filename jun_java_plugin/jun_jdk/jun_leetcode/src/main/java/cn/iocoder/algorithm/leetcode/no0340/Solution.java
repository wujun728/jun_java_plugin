package cn.iocoder.algorithm.leetcode.no0340;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/longest-substring-with-at-most-k-distinct-characters/
 */
public class Solution {

    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k == 0) {
            return 0;
        }

        // 初始化变量
        int max = 0;
        Map<Character, Integer> characterIndexes = new HashMap<>(); // key：字符，value：最后出现的位置
        int maxAllowRepeats = k; // 最大可重复次数
        int begin = 0;

        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            Integer index = characterIndexes.get(ch);
            if (index != null) {
                max = Math.max(max, i - begin + 1);
            } else {
                if (characterIndexes.size() < maxAllowRepeats) {
                    max = Math.max(max, i - begin + 1);
                } else {
                    // 获得删除字母的位置，并且删除
                    int delIndex = Collections.min(characterIndexes.values());
                    characterIndexes.remove(s.charAt(delIndex));
                    begin = delIndex + 1;
                }
            }
            characterIndexes.put(ch, i);
        }

        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.lengthOfLongestSubstringKDistinct("a", 0));
    }

}
