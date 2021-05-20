package cn.iocoder.algorithm.leetcode.no0130;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * BFS
 */
public class Solution03 {

    private static final int[][] DIRECTIONS = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public void solve(char[][] board) {
        int n = board.length;
        if (n == 0) {
            return;
        }
        int m = board[0].length;

        // 标记边缘节点，以及其关联为 #
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 岛屿
                if (board[i][j] == 'X') {
                    continue;
                }
                // 已经被标记为边缘节点相关
                if (board[i][j] == '#') {
                    continue;
                }
                // 非边缘
                if (!isEDge(i, j, n, m)) {
                    continue;
                }
                // dfs 进行标记
                this.bfs(board, i, j, n, m);
            }
        }

        // 设置哪些是被小岛包围，哪些无法被包围
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'X') {
                    continue;
                }
                if (board[i][j] == '#') {
                    board[i][j] = 'O';
                    continue;
                }
                board[i][j] = 'X';
            }
        }
    }

    private void bfs(char[][] board, int i, int j, int n, int m) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        board[i][j] = '#';
        while (!queue.isEmpty()) {
            int[] ij = queue.poll();
            int x = ij[0];
            int y = ij[1];
            for (int[] direction : DIRECTIONS) {
                int p = x + direction[0];
                int q = y + direction[1];
                // 超过边界
                if (this.isOverLimit(p, q, n, m)) {
                    continue;
                }
                // 已经是岛屿或者被标记为边缘相关
                if (board[p][q] != 'O') {
                    continue;
                }
                // 标记
                board[p][q] = '#';
                // 添加到队列
                queue.add(new int[]{p, q});
            }
        }
    }

    private boolean isOverLimit(int i, int j, int n, int m) {
        return i < 0 || i >= n
                || j < 0 || j >= m;
    }

    private boolean isEDge(int i, int j, int n, int m) {
        return i == 0 || i == n - 1
                || j == 0 || j == m - 1;
    }

    public static void main(String[] args) {
//        char[][] board = new char[][]{
//                {'O', 'O', 'O'},
//                {'O', 'O', 'O'},
//                {'O', 'O', 'O'},
//        };
        char[][] board = new char[][]{
                {'O','O','O','O','O','O','O','O','X','O','O','O','O','O','X','O','O','O','O','O'},{'O','O','O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','O','O'},{'X','O','O','X','O','X','O','O','O','O','X','O','O','X','O','O','O','O','O','O'},{'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','X','X','O'},{'O','X','X','O','O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O'},{'O','O','O','O','X','O','O','O','O','O','O','X','O','O','O','O','O','X','X','O'},{'O','O','O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','O','O'},{'O','O','O','O','O','O','O','O','O','O','O','O','O','X','O','O','O','O','O','O'},{'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','X','O'},{'O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},{'O','O','O','O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','O'},{'O','O','O','O','X','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O'},{'O','O','O','O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','O'},{'X','O','O','O','O','O','O','O','O','X','X','O','O','O','O','O','O','O','O','O'},{'O','O','O','O','O','O','O','O','O','O','O','X','O','O','O','O','O','O','O','O'},{'O','O','O','O','X','O','O','O','O','O','O','O','O','X','O','O','O','O','O','X'},{'O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','X','O','X','O','O'},{'O','X','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},{'O','O','O','O','O','O','O','O','X','X','O','O','O','X','O','O','X','O','O','X'},{'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'}
        };
        Solution03 solution = new Solution03();
        solution.solve(board);
        System.out.println(Arrays.deepToString(board));
    }

}
