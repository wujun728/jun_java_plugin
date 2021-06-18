package cn.iocoder.algorithm.leetcode.no0433;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/minimum-genetic-mutation/
 *
 * BFS
 */
public class Solution {

    public int minMutation(String start, String end, String[] bank) {
        // 映射。key 为将指定位置转成 * 后，有哪些单词 value 。
        Map<String, List<String>> normalizedMap = this.normalize(bank);

        // BFS
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        Set<String> visited = new HashSet<>();
        visited.add(start);
        int counts = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                for (int j = 0; j < current.length(); j++) {
                    List<String> targets = normalizedMap.get(this.normalized(current, j));
                    if (targets == null) {
                        continue;
                    }
                    for (String target : targets) {
                        if (target.equals(end)) {
                            return counts + 1;
                        }
                        if (visited.contains(target)) {
                            continue;
                        }
                        visited.add(target);
                        queue.add(target);
                    }
                }
            }
            counts++;
        }

        return -1;
    }

    private Map<String, List<String>> normalize(String[] bank) {
        Map<String, List<String>> normalizedMap = new HashMap<>();
        Arrays.stream(bank).forEach(str -> {
            for (int i = 0; i < str.length(); i++) {
                String normalizedStr = this.normalized(str, i);
                List<String> array = normalizedMap.computeIfAbsent(normalizedStr, k -> new ArrayList<>());
                array.add(str);
            }
        });
        return normalizedMap;
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

}
