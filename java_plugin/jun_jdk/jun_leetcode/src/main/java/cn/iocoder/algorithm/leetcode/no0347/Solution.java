package cn.iocoder.algorithm.leetcode.no0347;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/top-k-frequent-elements/
 */
public class Solution {

    private class Node implements Comparable<Node> {

        private int value;
        private int count;

        public Node(int value, int count) {
            this.value = value;
            this.count = count;
        }

        @Override
        public int compareTo(Node o) {
            return this.count - o.count;
        }
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        // 计数
        Map<Integer, Integer> counts = new HashMap<>();
        Arrays.stream(nums).forEach(value -> counts.put(value, counts.getOrDefault(value, 0) + 1));

        // 堆排序
        Queue<Node> queue = new PriorityQueue<>(k);
        counts.forEach((value, count) -> {
            // 队列未满
            if (queue.size() < k) {
                queue.add(new Node(value, count));
                return;
            }
            // 队列已满
            if (count > queue.peek().count) {
                queue.remove();
                queue.add(new Node(value, count));
            }
        });

        // 输出结果
        Integer[] results = new Integer[k];
        for (int i = 0; i < k; i++) {
            results[k - i - 1] = queue.remove().value;
        }
        return Arrays.asList(results);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.topKFrequent(new int[]{3, 1, 1, 2, 2, 2}, 3));
    }

}
