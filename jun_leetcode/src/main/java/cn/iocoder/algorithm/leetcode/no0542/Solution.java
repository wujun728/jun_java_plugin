package cn.iocoder.algorithm.leetcode.no0542;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/01-matrix/
 *
 * BFS
 */
public class Solution {

    private int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private int n;
    private int m;
    private int[][] matrix;

    public int[][] updateMatrix(int[][] matrix) {
        this.n = matrix.length;
        this.m = matrix[0].length;
        this.matrix = matrix;
        int[][] result = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 0) {
                    continue;
                }
                boolean[][] visits = new boolean[n][m];
                result[i][j] = bfs(visits, i, j);
            }
        }
        return result;
    }

    private int bfs(boolean[][] visits, int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visits[x][y] = true;
        int length = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            length++;
            for (int k = 0; k < size; k++) {
                int[] ij = queue.poll();
                int i = ij[0];
                int j = ij[1];
                for (int[] direction : directions) {
                    int newI = i + direction[0];
                    int newJ = j + direction[1];
                    if (!this.inArea(newI, newJ)) {
                        continue;
                    }
                    if (visits[newI][newJ]) {
                        continue;
                    }
                    if (matrix[newI][newJ] == 0) {
                        return length;
                    }
                    visits[newI][newJ] = true;
                    queue.add(new int[]{newI, newJ});
                }
            }
        }
        throw new IllegalStateException("不会出现这个情况");
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[][] matrix = new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        int[][] matrix = new int[][]{{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
        System.out.println(Arrays.deepToString(solution.updateMatrix(matrix)));
    }

}
