package cn.iocoder.algorithm.leetcode.no0547;

/**
 * https://leetcode-cn.com/problems/friend-circles/
 *
 * DFS
 */
public class Solution {

    public int findCircleNum(int[][] M) {
        int n = M.length;
        if (n == 0) {
            return 0;
        }
        int m = M[0].length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            // i i 位置，非 0 ，说明自己还没被 dfs 递归掉
            if (M[i][i] == 0) {
                continue;
            }
            // 计数 + 1
            sum++;
            // 递归第 i 层的每个元素
            this.dfs(M, i, i, n, m);
        }
        return sum;
    }

    private void dfs(int[][] M, int i, int j, int n, int m) {
        if (M[i][j] == 0) {
            return;
        }
        // 标记为已访问
        M[i][j] = 0;
        M[j][i] = 0;
        // 递归第 j 层，说明相关的也是好友。
        for (int k = 0; k < m; k++) {
            this.dfs(M, j, k, n, m);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] M = new int[][]{
//                {1,1,0},
//                {1,1,0},
//                {0,0,1}
//                {1,1,0},
//                {1,1,1},
//                {0,1,1}
                {1, 0, 0, 1},
                {0, 1, 1, 0},
                {0, 1, 1, 1},
                {1, 0, 1, 1},
        };
        System.out.println(solution.findCircleNum(M));
    }

}
