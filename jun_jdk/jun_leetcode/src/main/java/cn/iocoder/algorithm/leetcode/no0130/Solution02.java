package cn.iocoder.algorithm.leetcode.no0130;

/**
 * DFS
 *
 * 实际上，只有边缘会出现无法被围绕的区域，所以我们只要判断到达边缘时，进行 DFS ，进行标记即可。
 * 也就说，非边缘以及其接壤的 O ，都可以被包围，变成 X 。
 */
public class Solution02 {

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
                this.dfs(board, i, j, n, m);
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

    private void dfs(char[][] board, int i, int j, int n, int m) {
        // 超过边界
        if (this.isOverLimit(i, j, n, m)) {
            return;
        }
        // 已经是岛屿或者被标记为边缘相关
        if (board[i][j] != 'O') {
            return;
        }

        // 标记
        board[i][j] = '#';

        // dfs 四周
        this.dfs(board, i - 1, j, n, m);
        this.dfs(board, i + 1, j, n, m);
        this.dfs(board, i, j - 1, n, m);
        this.dfs(board, i, j + 1, n, m);
    }

    private boolean isOverLimit(int i, int j, int n, int m) {
        return i < 0 || i >= n
                || j < 0 || j >= m;
    }

    private boolean isEDge(int i, int j, int n, int m) {
        return i == 0 || i == n - 1
                || j == 0 || j == m - 1;
    }

}
