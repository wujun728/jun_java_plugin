package cn.iocoder.algorithm.leetcode.no0451;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/sort-characters-by-frequency/
 */
public class Solution {

    private class Node implements Comparable<Node> {

        private Character value;
        private int count;

        public Node(Character value, int count) {
            this.value = value;
            this.count = count;
        }

        @Override
        public int compareTo(Node o) {
            return this.count - o.count;
        }
    }

    public String frequencySort(String s) {
        if (s.isEmpty()) {
            return "";
        }

        // 计数
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character value = s.charAt(i);
            counts.put(value, counts.getOrDefault(value, 0) + 1);
        }

        // 堆排序
        Queue<Node> queue = new PriorityQueue<>(counts.size());
        counts.forEach((value, count) -> queue.add(new Node(value, count)));

        // 输出结果
        String result = "";
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            result = this.gen(node.value.toString(), node.count) + result;
        }
        return result;
    }

    private String gen(String str, int count) {
        if (count == 1) {
            return str;
        }

        if (count % 2 == 1) {
            return str + this.gen(str + str, count / 2);
        }
        return this.gen(str + str, count / 2);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.frequencySort("tree"));
    }

}
