package com.leetcode.weekly.weekly125;

import org.junit.jupiter.api.Test;

/**
 * 车的可用捕获量
 *
 * @author BaoZhou
 * @date 2019/2/24
 */
public class NumRookCaptures {

    @Test
    public void test() {
        char[][] test = {{'.', '.', '.', '.', '.', '.', '.', '.'}, {'.', '.', '.', 'p', '.', '.', '.', '.'}, {'.', '.', '.', 'R', '.', '.', '.', 'p'}, {'.', '.', '.', '.', '.', '.', '.', '.'}, {'.', '.', '.', '.', '.', '.', '.', '.'}, {'.', '.', '.', 'p', '.', '.', '.', '.'}, {'.', '.', '.', '.', '.', '.', '.', '.'}, {'.', '.', '.', '.', '.', '.', '.', '.'}};
        System.out.println(numRookCaptures(test));
    }

    public int numRookCaptures(char[][] board) {
        int x = 0, y = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'R') {
                    x = i;
                    y = j;
                }
            }
        }
        int result = 0;

        int begin = x;
        while (begin >= 0) {
            if (board[begin][y] == 'B') {
                break;
            }
            if (board[begin][y] == 'p') {
                result++;
                break;
            }
            begin--;
        }

        begin = x;
        while (begin < board.length) {
            if (board[begin][y] == 'B') {
                break;
            }
            if (board[begin][y] == 'p') {
                result++;
                break;
            }
            begin++;
        }

        begin = y;
        while (begin >= 0) {
            if (board[x][begin] == 'B') {
                break;
            }
            if (board[x][begin] == 'p') {
                result++;
                break;
            }
            begin--;
        }

        begin = y;
        while (begin < board.length) {
            if (board[x][begin] == 'B') {
                break;
            }
            if (board[x][begin] == 'p') {
                result++;
                break;
            }
            begin++;
        }
        return result;
    }
}
