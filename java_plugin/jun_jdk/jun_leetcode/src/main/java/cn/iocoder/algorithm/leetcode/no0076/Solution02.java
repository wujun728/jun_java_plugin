package cn.iocoder.algorithm.leetcode.no0076;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参考 https://leetcode-cn.com/problems/minimum-window-substring/solution/zui-xiao-fu-gai-zi-chuan-by-leetcode-2/ 博客【方法 2】
 *
 * 滑动窗口
 */
public class Solution02 {

    public String minWindow(String s, String t) {
        // 忽略空串
        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        // 计算每个字符，需要匹配的数量
        Map<Character, Integer> needCounts = new HashMap<>(); // 每个字符，需要的数量。
        for (int i = 0; i < t.length(); i++) {
            needCounts.put(t.charAt(i), needCounts.getOrDefault(t.charAt(i), 0) + 1);
        }
        int needCount = needCounts.size(); // 需要匹配的字母数量

        // 计算需要匹配的字符列表
        List<Pair<Integer, Character>> pairs = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (needCounts.containsKey(ch)) {
                pairs.add(new Pair<>(i, ch));
            }
        }

        // 开始遍历
        int left = 0;
        int[] answers = new int[]{-1, -1, -1}; // 字符串的长度，开始位置，结束位置。
        Map<Character, Integer> windowCounts = new HashMap<>(); // 当前窗口，每个字符的数量。
        int windowCount = 0; // 当前窗口，匹配的字母数
        for (int right = 0; right < pairs.size(); right++) {
            // 增加当前字符的计数
            Pair<Integer, Character> pair = pairs.get(right);
            char rightCh = pair.getValue();
            windowCounts.put(rightCh, windowCounts.getOrDefault(rightCh, 0) + 1);

            // 判断是否到达阀值
            if (windowCounts.get(rightCh).equals(needCounts.get(rightCh))) {
                windowCount++;
            }

            // 如果全部到达阀值，计算最短长度
            while (windowCount == needCount
                && left <= right) {
                // 计算最小值
                int endIndex = pair.getKey();
                int startIndex = pairs.get(left).getKey();
                int length = endIndex - startIndex + 1;
                if (answers[0] == -1 || length < answers[0]) {
                    answers[0] =  length;
                    answers[1] = startIndex;
                    answers[2] = endIndex;
                }

                // 减小 left 对应的字母计数。因为后续，要先匹配到一个新的 left 的字母。
                char leftCh = pairs.get(left).getValue();
                windowCounts.put(leftCh, windowCounts.get(leftCh) -1);

                // 判断是否小于阀值，如果是，则减少。减少后，下面依次 while 就不会执行了
                // 重要：可以把这个 while 循环，寻找第一个打破满足条件的 left ，结束。这样，后续的 for 循环，就是为了满足这个条件。
                if (windowCounts.get(leftCh) < needCounts.get(leftCh)) {
                    windowCount--;
                }

                // left 到下一个位置
                left++;
            }
        }

        return answers[0] != -1 ? s.substring(answers[1], answers[2] + 1) : "";
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.minWindow("ADOBECODEBANC", "ABC"));
        System.out.println(solution.minWindow("ABBCA", "ABC"));
//        System.out.println(solution.minWindow("ADOBECODEBANC", "Z"));
//        System.out.println(solution.minWindow("a", "aa"));
    }

}

