package cn.iocoder.algorithm.leetcode.no0051;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/n-queens/
 *
 * 回溯算法
 */
public class Solution {

    private List<List<String>> result = new ArrayList<>();
    private int n;
    private int[] rows; // 行，记录每一行所使用的列
    private boolean[] cols; // 列（垂直）方向
    private boolean[] lefts; // / 方向
    private boolean[] rights; // \ 方向

    public List<List<String>> solveNQueens(int n) {
        // 初始化
        this.init(n);
        // 回溯
        this.backtracking(0);
        // 结果
        return result;
    }

    private void init(int n) {
        this.n = n;
        this.rows = new int[n];
        this.cols = new boolean[n];
        this.lefts = new boolean[2 * n];
        this.rights = new boolean[2 * n];
    }

    private void backtracking(int i) {
        // 满足条件，生成。
        if (i == n) {
            gen();
            return;
        }

        // 回溯
        for (int j = 0; j < n; j++) {
            if (cols[j]
                || lefts[this.leftIndex(i, j)]
                || rights[this.rightIndex(i, j)]) {
                continue;
            }
            // 标记已使用
            rows[i] = j;
            cols[j] = lefts[this.leftIndex(i, j)] = rights[this.rightIndex(i, j)] = true;
            // 回溯
            this.backtracking(i + 1);
            // 标记未使用
            cols[j] = lefts[this.leftIndex(i, j)] = rights[this.rightIndex(i, j)] = false;
        }
    }

    private void gen() {
        List<String> array = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            String str = "";
            for (int j = 0; j < n; j++) {
                str += rows[i] == j ? "Q" : ".";
            }
            array.add(str);
        }
        result.add(array);
    }

    private int leftIndex(int i, int j) {
        return n + i - j;
    }

    private int rightIndex(int i, int j) {
        return 2 * n - i - j - 1; // -1 ，避免越界。
    }

}
