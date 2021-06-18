package cn.iocoder.algorithm.leetcode.no0037;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/sudoku-solver/
 *
 * 回溯算法
 */
public class Solution {

    private char[][] board;
    private int n;
    private int diamondSize = 3;
    private boolean[][] horizontals;
    private boolean[][] verticals;
    private boolean[][] diamonds;

    public void solveSudoku(char[][] board) {
        // 初始化
        this.init(board);
        // 回溯
        this.backtracking(0, 0);
    }

    private void init(char[][] board) {
        this.board = board;
        this.n = board.length;
        this.horizontals = new boolean[n][n];
        this.verticals = new boolean[n][n];
        this.diamonds = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                int numberIndex = board[i][j] - '1';
                // 水平方向
                this.horizontals[i][numberIndex] = true;
                // 垂直方向
                this.verticals[j][numberIndex] = true;
                // 小方格
                this.diamonds[this.diamondIndex(i, j)][numberIndex] = true;
            }
        }
    }

    private int diamondIndex(int i, int j) {
        return i / diamondSize * diamondSize + j / diamondSize;
    }

    private boolean backtracking(int i, int j) {
        // 判断是否结束
        if (j == n) {
            i++;
            j = 0;
            if (i == n) {
                return true;
            }
        }

        if (board[i][j] == '.') {
            for (int number = 1; number <= n; number++) {
                int numberIndex = number - 1;
                // 校验
                if (horizontals[i][numberIndex]
                    || verticals[j][numberIndex]
                    || diamonds[this.diamondIndex(i, j)][numberIndex]) {
                    continue;
                }
                // 标记使用
                horizontals[i][numberIndex] = true;
                verticals[j][numberIndex] = true;
                diamonds[this.diamondIndex(i, j)][numberIndex] = true;
                // 回溯
                board[i][j] = (char) (number + '0');
                if (this.backtracking(i, j + 1)) {
                    return true;
                }
                board[i][j] = '.';
                // 标记未使用
                horizontals[i][numberIndex] = false;
                verticals[j][numberIndex] = false;
                diamonds[this.diamondIndex(i, j)][numberIndex] = false;
            }
            return false;
        } else {
            return this.backtracking(i, j + 1);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        char[][] board = new char[][]{
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };
        solution.solveSudoku(board);
        System.out.println(Arrays.deepToString(board));

    }

}
