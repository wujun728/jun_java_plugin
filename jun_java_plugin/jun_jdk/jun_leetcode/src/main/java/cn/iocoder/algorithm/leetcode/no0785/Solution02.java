package cn.iocoder.algorithm.leetcode.no0785;

import java.util.Arrays;

/**
 * DFS ，标记颜色的方式
 *
 * 如果想要 BFS 实现，可以将 {@link #dfs(int[][], int, int)} 方法，改成 BFS 的方式。
 */
public class Solution02 {

    /**
     * 颜色 - 未填写
     */
    public static final int COLOR_NONE = -1;
    /**
     * 颜色 - 黑色
     */
    public static final int COLOR_BLACK = 0;
    /**
     * 颜色 - 白色
     */
    public static final int COLOR_WHITE = 1;

    private int[] colors;

    public boolean isBipartite(int[][] graph) {
        // 初始化
        this.colors = new int[graph.length];
        Arrays.fill(colors, COLOR_NONE);

        // 逐层 dfs 。
        // 为什么需要逐层的原因是，有可能有些不相连，例如说，0 和 1 相连，2 和 3 相连，一样能形成二分图。
        // 如果是 0 和 1 相连，2 和 3 相连，并且 1 和 2 又相连，就可以避免 for 循环。
        for (int i = 0; i < graph.length; i++) {
            if (colors[i] != COLOR_NONE) {
                continue;
            }
            if (!this.dfs(graph, i, COLOR_BLACK)) {
                return false;
            }
        }

        return true;
    }

    private boolean dfs(int[][] graph, int i, int color) {
        if (colors[i] != COLOR_NONE) {
            return colors[i] == color;  // 已经着色，判断颜色是否冲突
        }
        // 着色
        this.colors[i] = color;

        // 逐个遍历连接的节点，判断是否颜色冲突
        for (int j : graph[i]) {
            if (!this.dfs(graph, j, 1 - color)) { // 通过 1 - color ，来实现颜色取反
                return false;
            }
        }
        return true;
    }

}
