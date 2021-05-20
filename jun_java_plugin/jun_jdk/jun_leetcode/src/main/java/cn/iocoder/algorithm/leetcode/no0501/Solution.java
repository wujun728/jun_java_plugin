package cn.iocoder.algorithm.leetcode.no0501;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/find-mode-in-binary-search-tree/
 */
public class Solution {

    /**
     * 最大数量
     */
    private int maxCount;
    /**
     * 当前数量
     */
    private int currentCount;

    /**
     * 最后的值
     */
    private Integer lastValue;

    /**
     * 符合最大数量的数组
     */
    private List<Integer> result = new ArrayList<>();


    public int[] findMode(TreeNode root) {
        if (root == null) {
            return new int[]{};
        }
        this.dfs(root);

        int[] array = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            array[i] = result.get(i);
        }
        return array;
    }

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }

        // 左节点
        this.dfs(node.left);

        // 当前节点
        if (lastValue != null && node.val == lastValue) {
            currentCount++;
        } else {
            currentCount = 1;
            this.lastValue = node.val;
        }

        if (currentCount > maxCount) {
            maxCount = currentCount;
            result.clear();
            result.add(node.val);
        } else if (currentCount == maxCount) {
            result.add(node.val);
        }

        // 右节点
        this.dfs(node.right);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode node = TreeNode.create(2, 1);
        System.out.println(Arrays.toString(solution.findMode(node)));
    }

}
