package cn.iocoder.algorithm.leetcode.no0417;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/pacific-atlantic-water-flow/
 *
 * 暴力 DFS
 *
 * 思路比较简单，每个节点都往太平洋和大西洋走一轮。
 * 当然，这样会有很多重复。
 */
public class Solution {

    private static final int[][] DIRECTIONS = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private int n;
    private int m;

    private boolean[][] visits;

    private boolean pacific; // 太平洋，左边 and 上边
    private boolean atlantic; // 大西洋，右边 and 下边

    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        // 初始化变量
        if (matrix.length == 0) {
            return Collections.emptyList();
        }
        this.n = matrix.length;
        this.m = matrix[0].length;
        this.visits = new boolean[n][m];

        // 开始 dfs
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 开始 dfs
                pacific = false;
                atlantic = false;
                this.dfs(matrix, i, j);

                // 判断是否符合
                if (pacific && atlantic) {
                    results.add(Arrays.asList(i, j));
                }
            }
        }
        return results;
    }

    private void dfs(int[][] matrix, int i, int j) {
        // 判断是否能到达
        if (i == 0 || j == 0) {
            pacific = true;
        }
        if (i == n -1 || j == m - 1) {
            atlantic = true;
        }
        if (pacific && atlantic) {
            return;
        }

        // 标记已经访问
        visits[i][j] = true;

        // 遍历四周
        for (int[] direction : DIRECTIONS) {
            int p = i + direction[0];
            int q = j + direction[1];
            if (!this.inArea(p, q)) {
                continue;
            }
            if (!visits[p][q] && matrix[i][j] >= matrix[p][q]) {
                this.dfs(matrix, p, q);
            }
        }

        // 标记未访问，因为后续需要继续使用 visits 数组
        visits[i][j] = false;
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

}
