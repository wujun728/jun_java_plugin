package cn.iocoder.algorithm.leetcode.no0127;

import java.util.*;

/**
 * 在 {@link Solution} 的基础上，略作优化，当然还是 BFS 。
 *
 * 主要优化是，{@link Solution#canConvert(String, String)} 有点傻，改成预生成可匹配的字符串
 */
public class Solution02 {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 预先生成，每个字符串可匹配的结果
        Map<String, List<String>> normalizedMap = new HashMap<>();
        wordList.forEach(str -> {
            for (int i = 0; i < str.length(); i++) {
                String normalizedStr = this.normalized(str, i);
                List<String> array = normalizedMap.computeIfAbsent(normalizedStr, k -> new ArrayList<>());
                array.add(str);
            }
        });

        // 遍历
        int count = 0;
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        while (!queue.isEmpty()) {
            count++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String str = queue.poll();
                for (int j = 0; j < str.length(); j++) {
                    List<String> array = normalizedMap.get(this.normalized(str, j));
                    if (array == null) {
                        continue;
                    }
                    for (String target : array) {
                        if (target.equals(endWord)) {
                            return count + 1;
                        }
                        if (visited.contains(target)) {
                            continue;
                        }
                        queue.add(target);
                        visited.add(target);
                    }
                }
            }
        }

        return 0;
    }

    /**
     * 将字符串，指定位置修改成 *
     */
    private String normalized(String str, int index) {
        if (index == 0) {
            return new StringBuilder(str.length())
                    .append("*")
                    .append(str, index + 1, str.length())
                    .toString();
        }
        if (index == str.length() - 1) {
            return new StringBuilder(str.length())
                    .append(str, 0, index)
                    .append('*')
                    .toString();
        }
        return new StringBuilder(str.length())
                .append(str, 0, index)
                .append('*')
                .append(str, index + 1, str.length()).toString();
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(solution.ladderLength("hit", "cog",
                new ArrayList<>(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"))));
        System.out.println(solution.ladderLength("hit", "cog",
                new ArrayList<>(Arrays.asList("hot", "dot", "dog", "lot", "log"))));
    }

}
