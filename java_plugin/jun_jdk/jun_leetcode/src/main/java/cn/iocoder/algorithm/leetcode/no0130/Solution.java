package cn.iocoder.algorithm.leetcode.no0130;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/surrounded-regions/
 *
 * DFS
 *
 * 思路错误，放弃，在 Solution02 修改
 */
@Deprecated
public class Solution {

    public void solve(char[][] board) {
        int n = board.length;
        if (n == 0) {
            return;
        }
        int m = board[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'X') {
                    continue;
                }
//                if (visits[i][j]) {
//                    return;
//                }
//                visits[i][j] = true;
                if (this.dfs(board, i, j, n, m)) {
                    board[i][j] = 'X';
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'B') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private boolean dfs(char[][] board, int i, int j, int n, int m) {
        if (i < 0 || i >= n
            || j < 0 || j >= m) {
            return false;
        }
        if (board[i][j] == 'X') {
            return true;
        }
        if (board[i][j] == 'B') {
            return true;
        }
        // 设置值
        board[i][j] = 'B';
        // 判断周边是否都是 X
//        boolean result = this.dfs(board, visits, i - 1, j, n, m);
//        if (result) {
//            result = this.dfs(board, visits, i + 1, j, n, m);
//            if (result) {
//                result = this.dfs(board, visits, i, j - 1, n, m);
//                if (result) {
//                    result = this.dfs(board, visits, i, j + 1, n, m);
//                }
//            }
//        }
//        if (!result) {
//            board[i][j] = 'O';
//            return false;
//        }
        if (!this.dfs(board, i - 1, j, n, m)
            || !this.dfs(board, i + 1, j, n, m)
            || !this.dfs(board, i, j - 1, n, m)
            || !this.dfs(board, i, j + 1, n, m)) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
//        char[][] board = new char[][]{
//                {'O', 'O', 'O'},
//                {'O', 'O', 'O'},
//                {'O', 'O', 'O'},
//        };
//        char[][] board = new char[][]{
//                {'X','O','O','X','X','X','O','X','O','O'},
//                {'X','O','X','X','X','X','X','X','X','X'},
//                {'X','X','X','X','O','X','X','X','X','X'},
//                {'X','O','X','X','X','O','X','X','X','O'},
//                {'O','X','X','X','O','X','O','X','O','X'},
//                {'X','X','O','X','X','O','O','X','X','X'},
//                {'O','X','X','O','O','X','O','X','X','O'},
//                {'O','X','X','X','X','X','O','X','X','X'},
//                {'X','O','O','X','X','O','X','X','O','O'},
//                {'X','X','X','O','O','X','O','X','X','O'}
//        };
//        char[][] board = new char[][]{
//                {'O','X','X','O','X'},
//                {'X','O','O','X','O'},
//                {'X','O','X','O','X'}, // 第 3 个
//                {'O','X','O','O','O'},
//                {'X','X','O','X','O'}
//        };
//        char[][] board = new char[][]{
//                {'O','O','O','O','X','X'},
//                {'O','O','O','O','O','O'},
//                {'O','X','O','X','O','O'},
//                {'O','X','O','O','X','O'},
//                {'O','X','O','X','O','O'},
//                {'O','X','O','O','O','O'}
//        };
//        char[][] board = new char[][]{
//                {'O','X','X','O','X'},
//                {'X','O','O','X','O'},
//                {'X','O','X','O','X'},
//                {'O','X','O','O','O'},
//                {'X','X','O','X','O'}
//        };
        char[][] board = new char[][]{
                {'O','X','X','O','X'},
                {'X','O','O','X','O'},
                {'X','O','X','O','X'},
                {'O','X','O','O','O'},
                {'X','X','O','X','O'}
        };
        Solution solution = new Solution();
        solution.solve(board);
        System.out.println(Arrays.deepToString(board));
    }

}
