package cn.iocoder.algorithm.leetcode.no0733;

/**
 * https://leetcode-cn.com/problems/flood-fill/
 *
 * DFS
 */
public class Solution {

    private int n;
    private int m;

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        // 初始化
        if (image.length == 0) {
            return image;
        }
        if (image[sr][sc] == newColor) {
            return image;
        }
        this.n = image.length;
        this.m = image[0].length;

        // dfs
        this.dfs(image, sr, sc, image[sr][sc], newColor);
        return image;
    }

    private void dfs(int[][] image, int i, int j, int oldColor, int newColor) {
        if (!this.inArea(i, j)) {
            return;
        }
        if (image[i][j] != oldColor) {
            return;
        }
        image[i][j] = newColor;

        this.dfs(image, i + 1, j, oldColor, newColor);
        this.dfs(image, i - 1, j, oldColor, newColor);
        this.dfs(image, i, j + 1, oldColor, newColor);
        this.dfs(image, i, j - 1, oldColor, newColor);
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

}
