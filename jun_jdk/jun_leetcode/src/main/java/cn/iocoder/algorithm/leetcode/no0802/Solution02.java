package cn.iocoder.algorithm.leetcode.no0802;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/find-eventual-safe-states/
 *
 * 拓扑排序，通过 BFS 实现
 */
public class Solution02 {

    public List<Integer> eventualSafeNodes(int[][] graph) {
        // 初始化
        int n = graph.length;
        int[] counts = new int[n];
        List<List<Integer>> inArray = new ArrayList<>(); // 到这个节点，有哪些节点
        for (int i = 0; i < n; i++) {
            inArray.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            counts[i] = graph[i].length;
            for (int j = 0; j < graph[i].length; j++) {
                inArray.get(graph[i][j]).add(i);
            }
        }

        // BFS 遍历
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (counts[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            int index = queue.poll();
            for (int nextIndex : inArray.get(index)) {
                counts[nextIndex]--;
                if (counts[nextIndex] == 0) {
                    queue.add(nextIndex);
                }
            }
        }

        // 拼接结果
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (counts[i] == 0) {
                result.add(i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        solution.eventualSafeNodes(new int[][]{
//                {1, 2},
//                {2, 3},
//                {5},
//                {0},
//                {5},
//                {},
//                {}
//        });
        System.out.println(solution.eventualSafeNodes(new int[][]{
                {6, 7, 9},
                {},
                {},
                {},
                {2, 6, 8},
                {7, 9},
                {7, 8, 9},
                {},
                {6, 9},
                {7}
        }));
    }

}
