package cn.iocoder.algorithm.leetcode.no0417;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * DFS 优化，从边缘开始递归。
 *
 * 通过这样的方式，倒推每个节点，是否能到指定边缘，从而
 */
public class Solution02 {

    private int n;
    private int m;
    private boolean[][] pacifics;
    private boolean[][] lantics;

    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        // 初始化变量
        if (matrix.length == 0) {
            return Collections.emptyList();
        }
        this.n = matrix.length;
        this.m = matrix[0].length;
        this.pacifics = new boolean[n][m]; // 太平洋，左边 and 上边
        this.lantics = new boolean[n][m]; // 大西洋，右边 and 下边

        // 每一行
        for (int i = 0; i < n; i++) {
            this.dfs(matrix, i, 0, this.pacifics, matrix[i][0]);
            this.dfs(matrix, i, m - 1, this.lantics, matrix[i][m - 1]);
        }

        // 每一列
        for (int j = 0; j < m; j++) {
            this.dfs(matrix, 0, j, this.pacifics, matrix[0][j]);
            this.dfs(matrix, n - 1, j, this.lantics, matrix[n - 1][j]);
        }

        // 结果
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!pacifics[i][j] || !lantics[i][j]) {
                    continue;
                }
                results.add(Arrays.asList(i, j));
            }
        }
        return results;
    }

    private void dfs(int[][] matrix, int i, int j, boolean[][] flow, int prev) {
        // 超过范围
        if (!this.inArea(i, j)) {
            return;
        }
        // 无法流入
        if (matrix[i][j] < prev) {
            return;
        }

        // 标记可以流入
        if (flow[i][j]) {
            return;
        }
        flow[i][j] = true;

        // dfs 四周
        this.dfs(matrix, i - 1, j, flow, matrix[i][j]);
        this.dfs(matrix, i + 1, j, flow, matrix[i][j]);
        this.dfs(matrix, i, j - 1, flow, matrix[i][j]);
        this.dfs(matrix, i, j + 1, flow, matrix[i][j]);
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

}
