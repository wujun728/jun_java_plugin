package cn.iocoder.algorithm.leetcode.no0785;

/**
 * https://leetcode-cn.com/problems/is-graph-bipartite/
 *
 * DFS
 *
 * 龟速通过 25ms 。
 * 思路是，尝试将节点分成两组。如果分成功，就是二分图，分不成功，就是非二分图。
 */
public class Solution {

    public boolean isBipartite(int[][] graph) {
        if (graph.length == 0) {
            return false;
        }
        return this.isBipartite(graph, 0, 0,
                new boolean[graph.length], new boolean[graph.length]);
    }

    private boolean isBipartite(int[][] graph, int i, int j, boolean[] lefts, boolean[] rights) {
        // 到达边界
        if (i >= graph.length) {
            return true;
        }
        if (j == graph[i].length) {
            return this.isBipartite(graph, i + 1, 0, lefts, rights);
        }

        // 不符合的情况，已经全部在单边
        int left = i;
        int right = graph[i][j];
        if (lefts[left] && lefts[right]) {
            return false;
        }
        if (rights[left] && rights[right]) {
            return false;
        }

        // 直接是已经使用的节点，跳到下一个
        if (lefts[left] && rights[right]) {
            return this.isBipartite(graph, i, j + 1, lefts, rights);
        }
        if (rights[left] && lefts[right]) {
            return this.isBipartite(graph, i, j + 1, lefts, rights);
        }

        // 尝试去使用
        if (lefts[i] || !rights[i]) { // lefts[i] 处理，i 已经在 left 的情况下，!rights[i] 处理 i 即不在 left 也不在 right 的情况
            if (this.isBipartiteCommon(graph, i, j, left, right, lefts, rights)) {
                return true;
            }
        }
        if (rights[i] || !lefts[i]) { // 同上
            if (this.isBipartiteCommon(graph, i, j, left, right, rights, lefts)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBipartiteCommon(int[][] graph, int i, int j, int left, int right, boolean[] firsts, boolean[] seconds) {
        boolean oldFirst = firsts[left];
        boolean oldSecond = seconds[right];
        firsts[left] = seconds[right] = true;
        if (this.isBipartite(graph, i, j + 1, firsts, seconds)) {
            return true;
        }
        firsts[left] = oldFirst;
        seconds[right] = oldSecond;
        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[][] graph = new int[][]{
//                {1, 3},
//                {0, 2},
//                {1, 3},
//                {0, 2}
//        };
//        int[][] graph = new int[][]{
//                {1, 2, 3},
//                {0, 2},
//                {0, 1, 3},
//                {0, 2}
//        };
//        int[][] graph = new int[][]{
//                {1, 2, 3},
//                {0, 2},
//                {0, 1, 3},
//                {0, 2}
//        };
        int[][] graph = new int[][]{
                {1},
                {0},
                {3},
                {2}
        };
        System.out.println(solution.isBipartite(graph));
    }

}
