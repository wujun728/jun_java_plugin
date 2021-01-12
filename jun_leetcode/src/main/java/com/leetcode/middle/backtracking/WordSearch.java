package com.leetcode.middle.backtracking;

import org.junit.jupiter.api.Test;

/**
 * 单词搜索
 * @author BaoZhou
 * @date 2019/1/6
 */
public class WordSearch {
    @Test
    void test() {
        char[][] board = {

                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        System.out.println(exist(board, "ABC"));
        System.out.println(exist(board, "A"));
        System.out.println(exist(board, "ABCB"));
        System.out.println(exist(board, "SEE"));
        System.out.println(exist(board, "ESE"));
        System.out.println(exist(board, "EEDAF"));
        System.out.println(exist(board, "EEDAS"));
    }

    public boolean exist(char[][] board, String word) {
        if (board.length * board[0].length < word.length()) {
            return false;
        }
        boolean use[][] = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (backtrack(board, word, use, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;

    }

    private boolean backtrack(char[][] board, String word, boolean[][] use, int x, int y, int pos) {
        if (pos == word.length()) {
            return true;
        } else if (x > board.length - 1 || y > board[0].length - 1 || x < 0 || y < 0) {
            return false;
        }
        boolean right = false, left = false, up = false, down = false;
        if (board[x][y] == word.charAt(pos) && !use[x][y]) {
            use[x][y] = true;
            pos++;
            right = backtrack(board, word, use, x + 1, y, pos);
            if (right) {
                return true;
            }
            left = backtrack(board, word, use, x - 1, y, pos);
            if (left) {
                return true;
            }
            up = backtrack(board, word, use, x, y - 1, pos);
            if (up) {
                return true;
            }
            down = backtrack(board, word, use, x, y + 1, pos);
            if (down) {
                return true;
            }
            use[x][y] = false;
            pos--;
        }
        return false;
    }
}
