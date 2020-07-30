package cn.iocoder.algorithm.leetcode.no0003;

import java.util.Arrays;

/**
 * 在 {@link Solution} 的基础上，将 Map 改成数组，因为字母有限
 */
public class Solution02 {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // 初始化一堆遍历，默认使用第一个字符
        int begin = 0;
        int max = 1;
        int[] characterIndexes = new int[200];
        Arrays.fill(characterIndexes, -1);
        characterIndexes[s.charAt(0)] = 0;

        for (int i = 1; i < s.length(); i++) {
            int index = characterIndexes[s.charAt(i)];
            // 已经存在
            if (index >= begin) {
                begin = index + 1;
            } else {
                max = Math.max(max, i - begin + 1);
            }
            characterIndexes[s.charAt(i)] = i;
        }

        return max;
    }

}
