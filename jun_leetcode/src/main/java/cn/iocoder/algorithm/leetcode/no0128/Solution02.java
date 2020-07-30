package cn.iocoder.algorithm.leetcode.no0128;

import java.util.HashMap;
import java.util.Map;

/**
 * 并查集
 */
public class Solution02 {

    public int longestConsecutive(int[] nums) {
        UnionFind uf = new UnionFind(nums);
        return uf.getMax();
    }

    public class UnionFind {

        private Map<Integer, Integer> map = new HashMap<>();

        public UnionFind(int[] nums) {
            for (int num : nums) {
                map.put(num, num);
            }

            join();
        }

        public int findRoot(int i) {
            // 获得 root
            int root = i;
            while (map.get(root) != root) {
                root = map.get(root);
            }

            while (i != root) {
                i = map.put(i, root);
            }

            return root;
        }

        public void join() {
            for (Integer key : map.keySet()) {
                if (map.containsKey(key + 1)) {
                    join(key, key + 1);
                }
            }
        }

        public void join(int p, int q) {
            int pRoot = this.findRoot(p);
            int qRoot = this.findRoot(q);
            if (pRoot != qRoot) {
                if (pRoot > qRoot) {
                    map.put(pRoot, qRoot);
                } else {
                    map.put(qRoot, pRoot);
                }
            }
        }

        // 因为并查集压缩完后，并不能保证所有的都指向同一个父类
        public int getMax() {
            int max = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                // 如果有比它大的，这里就不计算，交给后面的
                if (map.containsKey(entry.getKey() + 1)) {
                    continue;
                }
                // 通过并查集，减少计算次数。
                max = Math.max(max, entry.getKey() - this.findRoot(entry.getValue()) + 1);
            }
            return max;
        }

    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(solution.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
//        System.out.println(solution.longestConsecutive(new int[]{1, 0, -1}));
    }

}
