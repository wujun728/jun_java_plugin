package cn.iocoder.algorithm.leetcode.no0159;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/longest-substring-with-at-most-two-distinct-characters/
 */
public class Solution {

    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // 初始化变量
        int max = 0;
        Map<Character, List<Integer>> characterIndexes = new HashMap<>(); // key：字符，value：位置
        int maxAllowRepeats = 2; // 最大可重复次数
        int begin = 0;

        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            List<Integer> indexes = characterIndexes.get(ch);
            if (indexes != null) {
                max = Math.max(max, i - begin + 1);
            } else {
                if (characterIndexes.size() < maxAllowRepeats) {
                    max = Math.max(max, i - begin + 1);
                } else {
                    // 获得开始字母的最大位置，移除之前的所有字母
                    begin = this.findNewBegin(s, i - 1, maxAllowRepeats - 1) + 1;
                    int finalBegin = begin; // lambada 表达式需要 final
                    for (Iterator<Map.Entry<Character, List<Integer>>> it = characterIndexes.entrySet().iterator(); it.hasNext();){
                        List<Integer> positions = it.next().getValue();
                        positions.removeIf(position -> position < finalBegin);
                        if (positions.isEmpty()) {
                            it.remove();
                        }
                    }
                }
                indexes = new ArrayList<>();
                characterIndexes.put(ch, indexes);
            }
            indexes.add(i);
        }

        return max;
    }

    private int findNewBegin(String s, int begin, int maxAllowRepeats) {
        Set<Character> exists = new HashSet<>();
        for (int i = begin; i >= 0; i--) {
            exists.add(s.charAt(i));
            if (exists.size() > maxAllowRepeats) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.lengthOfLongestSubstringTwoDistinct("bacc"));
    }

}
