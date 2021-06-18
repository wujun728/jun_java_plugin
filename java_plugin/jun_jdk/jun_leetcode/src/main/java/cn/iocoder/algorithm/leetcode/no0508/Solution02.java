package cn.iocoder.algorithm.leetcode.no0508;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 在 {@link Solution} 的基础上，去掉 stream 可能带来的性能降低
 */
public class Solution02 {

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
    private int maxSize = 0;

    public int[] findFrequentTreeSum(TreeNode root) {
        this.doSum(root);

        int[] arrays = new int[maxSize];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (!entry.getValue().equals(maxCount)) {
                continue;
            }
            arrays[index] = entry.getKey();
            index++;
            if (index >= maxSize) {
                break;
            }
        }
        return arrays;
    }

    private int doSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 求以 root 为根节点的值
        int sum = root.val + this.doSum(root.left)
                + this.doSum(root.right);

        // 计数
        Integer count = counts.get(sum);
        if (count == null) {
            count = 1;
        } else {
            count++;
        }
        counts.put(sum, count);
        if (count > maxCount) {
            maxSize = 1;
            maxCount = count;
        } else if (count == maxCount) {
            maxSize++;
        }

        return sum;
    }

}
