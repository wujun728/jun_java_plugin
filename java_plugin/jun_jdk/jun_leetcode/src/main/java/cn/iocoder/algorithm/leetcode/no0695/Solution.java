package cn.iocoder.algorithm.leetcode.no0695;

/**
 * https://leetcode-cn.com/problems/max-area-of-island/
 *
 * DFS
 */
public class Solution {

    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                if (grid[i][j] == 1) {
                    max = Integer.max(max, this.dfs(grid, i, j));
                }
            }
        }

        return max;
    }

    private int dfs(int[][] grid, int i, int j) {
        int n = grid.length, m = grid[0].length;
        if (i < 0 || i >= n || j < 0 || j >= m) {
            return 0;
        }
        if (grid[i][j] == 0) {
            return 0;
        }
//        System.out.println(i + "\t" + j);
        grid[i][j] = 0; // 标记为已访问。
        return 1 + this.dfs(grid, i + 1, j)
               + this.dfs(grid, i - 1, j)
               + this.dfs(grid, i, j + 1)
               + this.dfs(grid, i, j - 1);
    }

    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 0, 0},
                {1, 0, 1},
                {1, 1, 1}
        };

        Solution solution = new Solution();
        System.out.println(solution.maxAreaOfIsland(grid));
    }

}
