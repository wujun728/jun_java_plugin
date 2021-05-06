package cn.iocoder.algorithm.leetcode.no0684;

/**
 * https://leetcode-cn.com/problems/redundant-connection/
 *
 * 并查集
 */
public class Solution {

    private class UnionFind {

        private int[] array;

        public UnionFind(int n) {
            this.array = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                array[i] = i;
            }
        }

        public int findRoot(int i) {
            // 寻找 root
            int root = i;
            while (array[root] != root) {
                root = array[root];
            }

            // 压缩
            // 在此题中，不需要进行压缩，会更加快。🙂 注意。
            while (i != root) {
                int tmp = array[i];
                array[i] = root;
                i = tmp;
            }

            return root;
        }

        public boolean connect(int p, int q) {
            int pRoot = this.findRoot(p);
            int qRoot = this.findRoot(q);
            if (pRoot == qRoot) {
                return true;
            }

            // 修改指向
            array[pRoot] = qRoot;
            return false;
        }

    }


    public int[] findRedundantConnection(int[][] edges) {
//        // 获得最大的节点
//        int max = -1;
//        for (int[] edge : edges) {
//            max = Math.max(max, edge[1]);
//        }

        // 并查集
        UnionFind find = new UnionFind(edges.length);
        for (int[] edge : edges) {
            if (find.connect(edge[0], edge[1])) {
                return edge;
            }
        }

        throw new IllegalStateException("不存在这个情况");
    }

}
