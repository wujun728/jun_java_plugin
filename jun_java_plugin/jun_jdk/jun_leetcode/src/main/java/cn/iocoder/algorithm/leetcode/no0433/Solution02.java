package cn.iocoder.algorithm.leetcode.no0433;

import java.util.*;

/**
 * BFS
 *
 * 和 {@link Solution} 的差别，在于匹配逻辑的修改。
 * 相比来说，性能更快。
 */
public class Solution02 {

    public int minMutation(String start, String end, String[] bank) {
        // BFS
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        boolean[] visited = new boolean[bank.length];
        int counts = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                for (int j = 0; j < bank.length; j++) {
                    String target = bank[j];
                    if (!this.canConvert(current, target)) {
                        continue;
                    }
                    if (target.equals(end)) {
                        return counts + 1;
                    }
                    if (visited[j]) {
                        continue;
                    }
                    visited[j] = true;
                    queue.add(target);
                }
            }
            counts++;
        }

        return -1;
    }

    private boolean canConvert(String from, String to) {
        int count = 0;
        for (int i = 0; i < from.length(); i++) {
            if (from.charAt(i) != to.charAt(i)) {
                count++;
                if (count >= 2) {
                    return false;
                }
            }
        }
        return true;
    }

}
