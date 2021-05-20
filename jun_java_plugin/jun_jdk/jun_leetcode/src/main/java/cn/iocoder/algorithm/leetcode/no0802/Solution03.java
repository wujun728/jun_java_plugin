package cn.iocoder.algorithm.leetcode.no0802;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/find-eventual-safe-states/
 *
 * DFS + 标记
 */
public class Solution03 {

    /**
     * 访问标识 - 未访问
     */
    private static final int VISIT_NO = 0;
    /**
     * 访问标识 - 已访问
     */
    private static final int VISIT_YES = 1;
    /**
     * 访问标识 - 已访问 + 未成环（安全）
     */
    private static final int VISIT_SAFE = 2;
    /**
     * 访问标识 - 已访问 + 成环（危险）
     */
    private static final int VISIT_DANGER = 3;

    public List<Integer> eventualSafeNodes(int[][] graph) {
        // 初始化
        int n = graph.length;
        int[] visits = new int[n];
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (this.dfs(i, graph, visits) == VISIT_SAFE) {
                result.add(i);
            }
        }
        return result;
    }

    private int dfs(int index, int[][] graph, int[] visits) {
        // 已经访问过，说明成环了
        if (visits[index] == VISIT_YES) {
            visits[index] = VISIT_DANGER;
            return VISIT_DANGER;
        }
        // 已经遍历过，返回对应结果
        if (visits[index] != VISIT_NO) {
            return visits[index];
        }
        // 标记已经访问
        visits[index] = VISIT_YES;

        // 遍历下一个节点，但凡有一个成环，那么自己就成环
        for (int nextIndex : graph[index]) {
            if (this.dfs(nextIndex, graph, visits) == VISIT_DANGER) {
                visits[index] = VISIT_DANGER;
                return visits[index];
            }
        }

        // 如果遍历都不成环，说明安全
        visits[index] = VISIT_SAFE;
        return visits[index];
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
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
