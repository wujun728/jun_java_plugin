package cn.iocoder.algorithm.leetcode.no0802;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/find-eventual-safe-states/
 *
 * 拓扑排序
 */
public class Solution {

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

        // 遍历
        boolean[] visits = new boolean[n];
        for (int i = 0; i < n; i++) {
//            try {
//
//            } catch (StackOverflowError e) {
//                e.printStackTrace();
//            }
            this.dfs(i, inArray, counts, visits);
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

    private void dfs(int index, List<List<Integer>> inArray, int[] counts, boolean[] visits) {
        if (counts[index] > 0) {
            return;
        }
        if (visits[index]) {
            return;
        }
        visits[index] = true;
        for (int nextIndex : inArray.get(index)) {
            counts[nextIndex]--;
            this.dfs(nextIndex, inArray, counts, visits);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
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
                {0, 6, 7, 9},
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
