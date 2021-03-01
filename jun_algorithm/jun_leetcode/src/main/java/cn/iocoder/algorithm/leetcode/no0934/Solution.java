package cn.iocoder.algorithm.leetcode.no0934;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/shortest-bridge/
 *
 * 两层 BFS
 */
public class Solution {

    private static int[][] DIRECTIONS = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private int[][] A;
    private int n;
    private int m;
    private Queue<int[]> queue;
    private boolean[][] visits;

    public int shortestBridge(int[][] A) {
        // 初始化
        this.A = A;
        this.n = A.length;
        this.m = A[0].length;
        this.queue = new LinkedList<>();
        this.visits = new boolean[n][m];


        // 获得第一个小岛
        this.findFirstIsland();

        // 走到第二个小岛
        return this.goToSecondIsland() - 1;
    }

    private void findFirstIsland() {
        Queue<int[]> startQueue = new LinkedList<>();
        // 获得首个 1 ，作为寻找岛屿的入口
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (A[i][j] == 0) {
                    continue;
                }
                int[] ij = new int[]{i, j};
                queue.add(ij);
                startQueue.add(ij);
                visits[i][j] = true;
                break;
            }
            if (!queue.isEmpty()) {
                break;
            }
        }

        // BFS 遍历岛屿
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
                if (A[x][y] == 0) { // 跳过非岛屿
                    continue;
                }
                if (visits[x][y]) { // 跳过已经访问
                    continue;
                }
                int[] xy = new int[]{x, y};
                queue.add(xy);
                startQueue.add(xy);
                visits[x][y] = true;
            }
        }

        // 设置最新队列
        this.queue = startQueue;
    }

    private int goToSecondIsland() {
        int count = 0;
        // BFS 遍历
        while (!queue.isEmpty()) {
            int size = queue.size();
            count++;
            for (int k = 0; k < size; k++) {
                int[] ij = queue.poll();
                int i = ij[0];
                int j = ij[1];
                for (int[] direction : DIRECTIONS) {
                    int x = i + direction[0];
                    int y = j + direction[1];
                    if (!this.inArea(x, y)) {
                        continue;
                    }
                    if (visits[x][y]) { // 跳过已经访问
                        continue;
                    }
                    if (A[x][y] == 1) {
                        return count;
                    }
                    int[] xy = new int[]{x, y};
                    queue.add(xy);
                    visits[x][y] = true;
                }
            }
        }
        throw new IllegalStateException("不存在这个情况");
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[][] A = new int[][]{{0, 1}, {1, 0}};
//        int[][] A = new int[][]{{0, 1, 0}, {0, 0, 0}, {0, 0, 1}};
        int[][] A = new int[][]{{1,1,1,1,1}, {1,0,0,0,1}, {1,0,1,0,1},
                {1,0,0,0,1}, {1,1,1,1,1}};
        System.out.println(solution.shortestBridge(A));
    }

}
