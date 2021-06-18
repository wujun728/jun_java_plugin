package cn.iocoder.algorithm.leetcode.no0653;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * BST 二分查找
 */
public class Solution02 {

    public boolean findTarget(TreeNode root, int k) {
        return this.dfs(root, root, k);
    }

    private boolean dfs(TreeNode root, TreeNode node, int k) {
        if (node == null) {
            return false;
        }

        // 左节点
        if (this.dfs(root, node.left, k)) {
            return true;
        }

        // 当前节点
        TreeNode found = this.find(root, k - node.val);
        if (found != null && found != node) {
            return true;
        }
        // 右节点
        return this.dfs(root, node.right, k);
    }

    private TreeNode find(TreeNode node, int val) {
        if (node == null) {
            return null;
        }
        // 匹配
        if (val == node.val) {
            return node;
        }

        // 二分查找
        return val > node.val ? this.find(node.right, val)
                : this.find(node.left, val);
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        TreeNode node = TreeNode.create(2, 1, 3);
        System.out.println(solution.findTarget(node, 4));
    }

}
