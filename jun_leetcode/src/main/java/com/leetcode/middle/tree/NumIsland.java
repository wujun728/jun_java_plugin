package com.leetcode.middle.tree;

import org.junit.jupiter.api.Test;

/**
 * @author BaoZhou
 * @date 2019/1/3
 */
public class NumIsland {

    @Test
    public void result() {
        //char[][] board =
        //        {{'1', '1', '1', '1', '1', '0', '0', '0', '0'},
        //                {'1', '1', '1', '1', '1', '0', '0', '0', '0'},
        //                {'1', '1', '1', '1', '1', '0', '0', '0', '0'},
        //                {'1', '1', '1', '1', '1', '0', '1', '1', '0'},
        //                {'1', '1', '1', '1', '1', '0', '0', '0', '0'},
        //                {'1', '1', '1', '1', '1', '0', '0', '0', '0'},
        //                {'1', '1', '1', '1', '1', '0', '0', '0', '0'},
        //                {'1', '1', '1', '1', '1', '0', '0', '0', '0'},
        //                {'1', '1', '1', '1', '1', '0', '0', '0', '0'},
        //                {'1', '1', '1', '1', '1', '0', '0', '0', '1'}};
        char[][] board =
                {{'1', '1', '0', '0', '0'},
                        {'1', '1', '0', '0', '0'},
                        {'0', '0', '1', '0', '0'},
                        {'0', '0', '0', '1', '1'},
                };
        System.out.println(numIslands(board));
    }

    public int numIslands(char[][] grid) {
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '0') {
                    continue;
                } else {
                    result++;
                    dfs(grid, i, j);
                }
            }
        }
        return result;
    }

    static int[][] search = { {1, 0},  {0, -1}, {0, 1}, {-1, 0}};

    private void dfs(char[][] grid, int i, int j) {
        if (grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        for (int i1 = 0; i1 < search.length; i1++) {
            int iPos = i + search[i1][0];
            int jPos = j + search[i1][1];
            if (iPos >= 0 && iPos < grid.length && jPos >= 0 && jPos < grid[0].length) {
                if (grid[iPos][jPos] == '1') {
                    dfs(grid, iPos, jPos);
                }
            }
        }
    }
}
