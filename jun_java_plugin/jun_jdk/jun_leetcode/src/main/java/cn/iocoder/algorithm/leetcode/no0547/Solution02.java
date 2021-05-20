package cn.iocoder.algorithm.leetcode.no0547;

/**
 * 并查集
 */
public class Solution02 {

    private class UnionFind {

        private int[] array;

        public int count;

        public UnionFind(int n) {
            this.array = new int[n];
            this.count = n;
            for (int i = 0; i < n; i++) {
                array[i] = i;
            }
        }

        public void union(int p, int q) {
            int pRoot = this.findRoot(p);
            int qRoot = this.findRoot(q);
            if (pRoot == qRoot) {
                return;
            }

            count--;
            array[pRoot] = qRoot;
        }

        private int findRoot(int i) {
            // 寻找 root
            int root = i;
            while (array[root] != root) {
                root = array[root];
            }

            // 路径压缩
            while (i != root) {
                int tmp = array[i];
                array[i] = root;
                i = tmp;
            }

            return root;
        }

    }

    public int findCircleNum(int[][] M) {
        int n = M.length;
        if (n == 0) {
            return 0;
        }
        int m = M[0].length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < m; j++) {
                if (M[i][j] == 0) {
                    continue;
                }
                uf.union(i, j);
            }
        }
        return uf.count;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        int[][] M = new int[][]{
//                {1,1,0},
//                {1,1,0},
//                {0,0,1}

                {1,1,0},
                {1,1,1},
                {0,1,1}
//                {1, 0, 0, 1},
//                {0, 1, 1, 0},
//                {0, 1, 1, 1},
//                {1, 0, 1, 1},
        };
        System.out.println(solution.findCircleNum(M));
    }

}
