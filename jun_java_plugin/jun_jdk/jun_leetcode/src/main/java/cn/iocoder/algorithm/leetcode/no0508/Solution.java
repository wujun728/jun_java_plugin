package cn.iocoder.algorithm.leetcode.no0508;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/most-frequent-subtree-sum/
 *
 * 这道题，leetcode 的描述，真让人蛋疼。简单来说
 *
 * 1. 子树必须包含所有叶子节点。
 * 2. 或者，子树只有自己作为根节点。
 */
public class Solution {

    /**
     * 计数
     *
     * key：求和
     * value：数量
     */
    private Map<Integer, Integer> counts = new HashMap<>();
    /**
     * 最大数量
     */
    private int maxCount = 0;

    public int[] findFrequentTreeSum(TreeNode root) {
        this.doSum(root);

        return counts.entrySet().stream().filter(entry -> entry.getValue().equals(maxCount))
                .mapToInt(Map.Entry::getKey).toArray();
    }

    private int doSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 求以 root 为根节点的值
        int sum = root.val + this.doSum(root.left)
                + this.doSum(root.right);

        // 计数
        int count = counts.compute(sum, (key, value) -> value == null ? 1 : value + 1);
        maxCount = Math.max(maxCount, count);

        return sum;
    }

}
