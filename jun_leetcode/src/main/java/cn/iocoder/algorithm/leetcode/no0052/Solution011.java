package cn.iocoder.algorithm.leetcode.no0052;

/**
 * 在 {@link Solution01} 的基础上，增加一些变量，减少 for 循环计算。
 */
public class Solution011 {

    private int total = 0;

    private int n;
    private boolean[] cols; // 列（垂直）方向
    private boolean[] lefts; // / 方向
    private boolean[] rights; // \ 方向

    public int totalNQueens(int n) {
        // 初始化
        this.init(n);
        // 回溯
        this.backtracking(0);
        // 结果
        return total;
    }

    private void init(int n) {
        this.n = n;
        this.cols = new boolean[n];
        this.lefts = new boolean[2 * n];
        this.rights = new boolean[2 * n];
    }

    private void backtracking(int i) {
        // 满足条件，生成。
        if (i == n) {
            total++;
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
            cols[j] = lefts[this.leftIndex(i, j)] = rights[this.rightIndex(i, j)] = true;
            // 回溯
            this.backtracking(i + 1);
            // 标记未使用
            cols[j] = lefts[this.leftIndex(i, j)] = rights[this.rightIndex(i, j)] = false;
        }
    }

    private int leftIndex(int i, int j) {
        return n + i - j;
    }

    private int rightIndex(int i, int j) {
        return 2 * n - i - j - 1; // -1 ，避免越界。
    }

}
