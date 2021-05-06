package cn.iocoder.algorithm.leetcode.no0130;

import java.util.Arrays;

/**
 * 并查集
 *
 * 感觉有点巧妙，主要是抽象了一个“虚拟节点”，表示边缘。
 */
public class Solution04 {

    public static class UnionFind {

        private int[] array;

        public UnionFind(int n) {
            this.array = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                array[i] = i;
            }
        }

        public void union(int p, int q) {
            if (p < 0 || q < 0
                || p >= array.length || q >= array.length) {
                return;
            }

            int pRoot = this.findRoot(p);
            int qRoot = this.findRoot(q);
            if (pRoot == qRoot) {
                return;
            }
            if (pRoot < qRoot) {
                array[pRoot] = qRoot;
            }  else {
                array[pRoot] = qRoot;
            }
        }

        public boolean isConnected(int p, int q) {
            if (p < 0 || q < 0
                    || p >= array.length || q >= array.length) {
                return false;
            }
            return this.findRoot(p) == this.findRoot(q);
        }

        private int findRoot(int i) {
            // 寻找 root 节点
            int root = i;
            while (array[root] != root) {
                root = array[root];
            }

            // 压缩路径
            while (i != root) {
                int tmp = array[i];
                array[i] = root;
                i = tmp;
            }

            return root;
        }

        public static int index(int i, int j, int n, int m) {
            if (i < 0 || j < 0) {
                return -1;
            }
            return i * m + j;
        }

    }

    public void solve(char[][] board) {
        int n = board.length;
        if (n == 0) {
            return;
        }
        int m = board[0].length;

        // 创建并查集，并进行初始化
        int dummy = n * m; // 虚拟节点，边缘节点，和它连接
        UnionFind uf = new UnionFind(n * m + 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'X') {
                    continue;
                }
                // 垂直方向
                int index = UnionFind.index(i, j, n, m);
                if (i == 0) {
                    uf.union(index, dummy);
                } else if (i == n - 1) {
                    // 连接下方
                    uf.union(index, dummy);
                    // 如果上方节点也为 O ，则进行连接
                    // 此处要注意，默认情况下，垂直方向，我们只连接上方。但是比较特殊的是，最后一行，要同时连接下方
                    // 下面在水平方向时，也会有类似逻辑
                    if (board[i - 1][j] == 'O') {
                        uf.union(index, UnionFind.index(i - 1, j, n, m)); // 上方节点
                    }
                } else {
                    if (board[i - 1][j] == 'O') {
                        uf.union(index, UnionFind.index(i - 1, j, n, m)); // 上方节点
                    }
                }
                // 水平方向
                if (j == 0) {
                    uf.union(index, dummy);
                } else if (j == m - 1) {
                    // 连接右边
                    uf.union(index, dummy);
                    // 如果左边节点也为 O ，则进行连接
                    if (board[i][j - 1] == 'O') {
                        uf.union(index, UnionFind.index(i, j - 1, n, m)); // 左边节点
                    }
                } else {
                    if (board[i][j - 1] == 'O') {
                        uf.union(index, UnionFind.index(i, j - 1, n, m)); // 左边节点
                    }
                }
            }
        }

        // 判断哪些和边缘是一个集合，进行标记
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'X') {
                    continue;
                }
                // 判断是否连接边缘
                int index = UnionFind.index(i, j, n, m);
                if (!uf.isConnected(index, dummy)) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    public static void main(String[] args) {
//        char[][] board = new char[][]{
//                {'X', 'X', 'X', 'X'},
//                {'X', 'O', 'O', 'X'},
//                {'X', 'X', 'O', 'X'},
//                {'X', 'O', 'X', 'X'},
//        };
//        char[][] board = new char[][]{
//                {'X','O','X','O','X','O'},
//                {'O','X','O','X','O','X'},
//                {'X','O','X','O','X','O'},
//                {'O','X','O','X','O','X'}
//        };
        char[][] board = new char[][]{
                {'0','X','X','O','X'},
                {'X','O','O','X','O'},
                {'X','O','X','O','X'}, // 第 3 个
                {'0','X','O','O','O'}, // 第 3、4 个
                {'X','X','O','X','O'},
        };
        Solution04 solution = new Solution04();
        solution.solve(board);
        System.out.println(Arrays.deepToString(board));
    }

}
