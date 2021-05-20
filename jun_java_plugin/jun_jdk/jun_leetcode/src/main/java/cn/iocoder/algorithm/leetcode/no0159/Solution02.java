package cn.iocoder.algorithm.leetcode.no0159;

import java.util.*;

/**
 * 在 {@link Solution} 的基础上，进行优化
 */
public class Solution02 {

    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // 初始化变量
        int max = 0;
        Map<Character, Integer> characterIndexes = new HashMap<>(); // key：字符，value：最后出现的位置
        int maxAllowRepeats = 2; // 最大可重复次数
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
        Solution02 solution = new Solution02();
        System.out.println(solution.lengthOfLongestSubstringTwoDistinct("abaccc"));
    }

}
