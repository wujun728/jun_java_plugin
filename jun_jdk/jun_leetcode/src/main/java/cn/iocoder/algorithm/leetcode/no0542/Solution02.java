package cn.iocoder.algorithm.leetcode.no0542;

import java.util.LinkedList;
import java.util.Queue;

/**
 * BFS ，从 0 节点开始遍历
 *
 * 参考 https://leetcode-cn.com/problems/01-matrix/solution/java-si-lu-jie-xi-bfsyan-du-you-xian-suan-fa-by-wa/
 */
public class Solution02 {

    private static int[][] DIRECTIONS = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * 因为总元素不超过 10000 个
     */
    public static final int MAX = 10000;

    private int n;
    private int m;

    public int[][] updateMatrix(int[][] matrix) {
        this.n = matrix.length;
        this.m = matrix[0].length;
        boolean[][] visits = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();
        // 将 0 节点，作为起点，添加到队列中。
        // 并且，标记非 0 节点为 MAX ，因为后续，我们会将最小距离，记录到 matrix 上。
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 0) {
                    queue.add(new int[]{i, j});
                    visits[i][j] = true;
                } else {
                    matrix[i][j] = MAX;
                }
            }
        }

        // bfs 开始
        while (!queue.isEmpty()) {
            int[] ij = queue.poll();
            int i = ij[0];
            int j = ij[1];
            int min = matrix[i][j];
            for (int[] direction : DIRECTIONS) {
                int x = i + direction[0];
                int y = j + direction[1];
                if (!this.inArea(x, y)) {
                    continue;
                }
                // 计算最小值
                min = Math.min(min, matrix[x][y] + 1);
                // 将未遍历的节点，并且非 0 节点，添加到队列
                if (matrix[x][y] == MAX && !visits[x][y]) {
                    queue.add(new int[]{x, y});
                    visits[x][y] = true;
                }
            }
            // 设置该节点的最小值
            matrix[i][j] = min;
        }
        return matrix;
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

}
