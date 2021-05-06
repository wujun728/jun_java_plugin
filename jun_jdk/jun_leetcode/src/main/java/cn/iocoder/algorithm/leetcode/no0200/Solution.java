package cn.iocoder.algorithm.leetcode.no0200;

/**
 * https://leetcode-cn.com/problems/number-of-islands/
 *
 * DFS
 *
 * 当然，也可以 BFS 。在
 */
public class Solution {

    public int numIslands(char[][] grid) {
        int counts = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '0') {
                    continue;
                }
                if (grid[i][j] == '1') {
                    counts++;
                    this.dfs(grid, i, j);
                }
            }
        }

        return counts;
    }

    // 将已经选中的，标记
    private void dfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length
            || j < 0 || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        this.dfs(grid, i + 1, j);
        this.dfs(grid, i - 1, j);
        this.dfs(grid, i, j + 1);
        this.dfs(grid, i, j - 1);
    }

}
