package cn.iocoder.algorithm.leetcode.no0212;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/word-search-ii/
 *
 * 回溯算法 + DFS
 */
public class Solution02 {

    private char[][] board;
    private int n;
    private int m;

    public List<String> findWords(char[][] board, String[] words) {
        if (board.length == 0) {
            return Collections.emptyList();
        }
        this.board = board;
        this.n = board.length;
        this.m = board[0].length;
        List<String> result = new ArrayList<>(); // 已匹配的集合
        List<String> wordList = Arrays.stream(words).collect(Collectors.toList()); // 需要被搞

        // 遍历 board 的每一个节点，作为开头
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                for (Iterator<String> it = wordList.iterator(); it.hasNext();) {
                    String word = it.next();
                    if (this.backtracking(i, j, word, 0)) {
                        it.remove();
                        result.add(word);
                    }
                }
            }
        }
        return result;
    }

    private boolean backtracking(int i, int j, String word, int index) {
        // 到达尾部，返回成功
        if (index == word.length()) {
            return true;
        }
        // 如果越界，直接返回失败
        if (!this.inArea(i, j)) {
            return false;
        }
        if (board[i][j] == '#') {
            return false;
        }
        // 判断字符不匹配
        char ch = word.charAt(index);
        if (board[i][j] != word.charAt(index)) {
            return false;
        }

        // 标记已经访问
        board[i][j] = '#';

        // 迭代后续位置字符
        index++;
        boolean result=  this.backtracking(i + 1, j, word, index)
                || this.backtracking(i - 1, j, word, index)
                || this.backtracking(i, j + 1, word, index)
                || this.backtracking(i, j - 1, word, index);

        // 标记未访问
        board[i][j] = ch;

        // 返回结果
        return result;
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

}
