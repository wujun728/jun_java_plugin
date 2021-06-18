package cn.iocoder.algorithm.leetcode.no0096;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/unique-binary-search-trees/
 *
 * 分治算法 + 缓存
 *
 * 比较粗暴，不是最佳解。
 */
public class Solution {

    /**
     * 因为结果固定，所以静态缓存
     */
    private static final Map<Integer, Integer> CACHE = new HashMap<>();

    public int numTrees(int n) {
        return partition(1, n);
    }

    public int partition(int low, int high) {
        if (low >= high) {
            return 1;
        }

        Integer cacheValue = CACHE.get(high - low);
        if (cacheValue != null) {
            return cacheValue;
        }

        int sum = 0;
        for (int i = low; i <= high; i++) {
            int leftCounts = this.partition(low, i - 1);
            int rightCounts = this.partition(i + 1, high);
            sum += leftCounts * rightCounts;
        }

        CACHE.put(high - low, sum);

        return sum;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.numTrees(18));
    }

}
