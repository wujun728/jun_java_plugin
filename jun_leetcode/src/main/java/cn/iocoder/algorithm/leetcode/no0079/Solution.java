package cn.iocoder.algorithm.leetcode.no0079;

/**
 * https://leetcode-cn.com/problems/word-search/
 *
 * 回溯算法
 */
public class Solution {

    private int n;
    private int m;
    private boolean[][] visits;

    public boolean exist(char[][] board, String word) {
        // 初始化变量
        if (board.length == 0) {
            return false;
        }
        this.n = board.length;
        this.m = board[0].length;
        this.visits = new boolean[n][m];

        // 选择一个点为起点，回溯
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (this.backtracking(board, i, j, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean backtracking(char[][] board, int x, int y, String word, int length) {
        // 判断是否越界
        if (!this.isInArea(x, y)) {
            return false;
        }
        // 已经访问，则跳过
        if (visits[x][y]) {
            return false;
        }
        // 判断访问的字符，是否匹配 word 对应的位置
        if (word.charAt(length) != board[x][y]) {
            return false;
        }

        // 标记已经访问，并比对是否已经匹配成功
        visits[x][y] = true;
        length++; // 匹配到的长度。无需使用字符串拼接，逐个比配字符即可
        if (word.length() == length) {
            return true;
        }

        // 继续回溯周边
        if (this.backtracking(board, x + 1, y, word, length)
            || this.backtracking(board, x - 1, y, word, length)
            || this.backtracking(board, x, y + 1, word, length)
            || this.backtracking(board, x, y - 1, word, length)) {
            return true;
        }

        // 标记未访问
        visits[x][y] = false;
        return false;
    }

    private boolean isInArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

}
