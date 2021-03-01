package cn.iocoder.algorithm.leetcode.no0542;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 在 {@link Solution02} 的基础上，继续优化
 *
 * 参考评论区 https://leetcode-cn.com/problems/01-matrix/comments/ 的 Angus-Liu 的解答
 */
public class Solution03 {

    private static int[][] DIRECTIONS = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private int n;
    private int m;

    public int[][] updateMatrix(int[][] matrix) {
        this.n = matrix.length;
        this.m = matrix[0].length;
        Queue<int[]> queue = new LinkedList<>();
        // 将 0 节点，作为起点，添加到队列中。
        // 并且，标记非 0 节点为 n + m ，因为后续，我们会将最小距离，记录到 matrix 上。因为，必然存在 0 ，而 0 到达 (i, j) 位置，一定不会超过 n + m 。
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 0) {
                    queue.add(new int[]{i, j});
                } else {
                    matrix[i][j] = n + m;
                }
            }
        }

        // bfs 开始
        while (!queue.isEmpty()) {
            int[] ij = queue.poll();
            int i = ij[0];
            int j = ij[1];
            for (int[] direction : DIRECTIONS) {
                int x = i + direction[0];
                int y = j + direction[1];
                if (!this.inArea(x, y)) {
                    continue;
                }
                // 如果当前位置，到达目标位置距离更近，则重新遍历目标位置
                if (matrix[x][y] > matrix[i][j] + 1) {
                    queue.add(new int[]{x, y});
                    matrix[x][y] = matrix[i][j] + 1;
                }
            }
        }
        return matrix;
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

}
