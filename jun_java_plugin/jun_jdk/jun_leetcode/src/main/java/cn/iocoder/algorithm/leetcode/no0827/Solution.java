package cn.iocoder.algorithm.leetcode.no0827;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/making-a-large-island/
 *
 * DFS
 */
public class Solution {

    private class Count {

        private int sum;

    }

    private Count[][] counts;

    private int[][] grid;
    private int n;
    private int m;

    public int largestIsland(int[][] grid) {
        // 初始化
        this.grid = grid;
        this.n = grid.length;
        this.m = grid[0].length;
        this.counts = new Count[n][m];

        // dfs
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) { // 海洋
                    continue;
                }
                if (counts[i][j] != null) {
                    continue;
                }
                // dfs
                Count count = new Count();
                this.dfs(i, j, count);
                // 求最大值
                max = Math.max(max, count.sum);
            }
        }

        // 求最大值
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) { // 小岛
                    continue;
                }
                max = Math.max(max, this.max(i, j));
            }
        }
        return max;
    }

    private void dfs(int i, int j, Count count) {
        if (!this.inArea(i, j)) {
            return;
        }
        // 已经访问过
        if (counts[i][j] != null) {
            return;
        }
        if (grid[i][j] == 0) {
            return;
        }
        counts[i][j] = count;
        count.sum++;

        // dfs 四周节点
        this.dfs(i + 1, j, count);
        this.dfs(i - 1, j, count);
        this.dfs(i, j + 1, count);
        this.dfs(i, j - 1, count);
    }

    private int max(int i, int j) {
        // 获得四周的点
        Set<Count> set = new HashSet<>();
        if (i > 0) {
            set.add(counts[i - 1][j]);
        }
        if (i < n - 1) {
            set.add(counts[i + 1][j]);
        }
        if (j > 0) {
            set.add(counts[i][j - 1]);
        }
        if (j < m - 1) {
            set.add(counts[i][j + 1]);
        }
        // 计算
        int sum = 0;
        for (Count count : set) {
            if (count == null) {
                continue;
            }
            sum += count.sum;
        }
        return sum + 1; // 1 是修改的结果。
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.largestIsland(new int[][]{
                {1, 0},
                {0, 1}
        }));
        System.out.println(solution.largestIsland(new int[][]{
                {1, 1},
                {0, 1}
        }));
        System.out.println(solution.largestIsland(new int[][]{
                {1}
        }));
    }

}
