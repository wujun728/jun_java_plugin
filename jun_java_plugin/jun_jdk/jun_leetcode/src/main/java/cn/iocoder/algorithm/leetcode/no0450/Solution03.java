package cn.iocoder.algorithm.leetcode.no0450;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 参考 https://leetcode-cn.com/problems/delete-node-in-a-bst/ 博客
 *
 * 相比 {@link Solution} 更简洁，性能更优
 */
public class Solution03 {

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        // 大于当前节点，说明在右边
        if (key > root.val) {
            root.right = this.deleteNode(root.right, key);
            return root;
        }
        // 小于当前节点，说明在左边
        if (key < root.val) {
            root.left = this.deleteNode(root.left, key);
            return root;
        }

        // 匹配，开始正式的表演.

        // 左节点为空，那么直接返回右子树即可。
        if (root.left == null) {
            return root.right;
        }
        // 右节点为空，那么直接返回左子树即可。
        if (root.right == null) {
            return root.left;
        }

        // 两边都非空。有两种解法：
        // 1. 选择左子树的最大值
        // 2. 选择右子树的最小值。
        // 我们选择第一种，美滋滋。实际效果是一样的。

        TreeNode leftMaxNode = this.findAndRemoveMaxNode(root.left, root, true);
        if (root.left == null) { // 如果是直接节点
            root.left = leftMaxNode.left;
        }
        root.val = leftMaxNode.val;
        return root;
    }

    private TreeNode findAndRemoveMaxNode(TreeNode node, TreeNode parent, boolean parentLeft) {
        if (node == null) {
            return null;
        }
        // 存在右节点
        if (node.right != null) {
            return this.findAndRemoveMaxNode(node.right, node, false);
        }

        // 不存在右节点，说明是最大节点
        this.removeNode(parent, parentLeft);
        return node;
    }

    private void removeNode(TreeNode parent, boolean parentLeft) {
        if (parentLeft) {
//            parent.left = null;
            parent.left = parent.left.right;
        } else {
//            parent.right = null;
            parent.right = parent.right.left;
        }
    }

    public static void main(String[] args) {
        if (true) {
            TreeNode node = TreeNode.create(3, 2, 4, 1);
            System.out.println(new Solution03().deleteNode(node, 3));
        }

    }

}
