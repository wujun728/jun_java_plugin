package cn.iocoder.algorithm.leetcode.no0230;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/kth-smallest-element-in-a-bst/
 */
public class Solution {

    private int cnt = 0;

    public int kthSmallest(TreeNode root, int k) {
        return this.dfs(root, k).val;
    }

    public TreeNode dfs(TreeNode root, int k) {
        if (root == null) {
            return null;
        }

        // 左节点
        TreeNode result = this.dfs(root.left, k);
        if (result != null) {
            return result;
        }

        // 中间节点
        cnt++;
        if (cnt == k) {
            return root;
        }

        // 右节点
        return this.dfs(root.right, k);
    }

}
