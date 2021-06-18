package cn.iocoder.algorithm.leetcode.no0127;

import java.util.*;

/**
 * 双向 BFS
 *
 * 相比单向来说，可以更早的中间点汇合，所以性能更好。
 */
public class Solution03 {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) {
            return 0;
        }
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
        Queue<String> beginQueue = new LinkedList<>();
        beginQueue.add(beginWord);
        Set<String> beginVisited = new HashSet<>();
        beginVisited.add(beginWord);
        Queue<String> endQueue = new LinkedList<>();
        endQueue.add(endWord);
        Set<String> endVisited = new HashSet<>();
        endVisited.add(endWord);
        while (!beginQueue.isEmpty() && !endQueue.isEmpty()) {
            count++;
            if (visit(normalizedMap, beginQueue, beginVisited, endVisited)) {
                return 2 * count;
            }
            if (visit(normalizedMap, endQueue, endVisited, beginVisited)) {
                return 2 * count + 1;
            }
        }

        return 0;
    }

    private boolean visit(Map<String, List<String>> normalizedMap,
                          Queue<String> queue,
                          Set<String> visited,
                          Set<String> otherVisited) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String str = queue.poll();
            for (int j = 0; j < str.length(); j++) {
                List<String> array = normalizedMap.get(this.normalized(str, j));
                if (array == null) {
                    continue;
                }
                for (String target : array) {
                    if (otherVisited.contains(target)) {
                        return true;
                    }
                    if (visited.contains(target)) {
                        continue;
                    }
                    queue.add(target);
                    visited.add(target);
                }
            }
        }
        return false;
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
        Solution03 solution = new Solution03();
//        System.out.println(solution.ladderLength("hit", "cog",
//                new ArrayList<>(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"))));

//        System.out.println(solution.ladderLength("hit", "cog",
//                new ArrayList<>(Arrays.asList("hot", "dot", "dog", "lot", "log"))));
        // hit->hot->dot->dog
        // cog 失败->log->lot

        System.out.println(solution.ladderLength("a", "c",
                new ArrayList<>(Arrays.asList("a", "b", "c"))));
    }

}
