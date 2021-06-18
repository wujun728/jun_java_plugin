package cn.iocoder.algorithm.leetcode.no0450;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 和 {@link Solution} 一样的思路，重新编码
 */
public class Solution02 {

    private TreeNode root;

    public TreeNode deleteNode(TreeNode root, int key) {
        this.root = root;

        // 检索并删除
        this.searchAndDeleteNode(root, null, key);

        return this.root;
    }

    private void searchAndDeleteNode(TreeNode node, TreeNode parent, int key) {
        if (node == null) {
            return;
        }

        if (key > node.val) {
            this.searchAndDeleteNode(node.right, node, key);
        } else if (key < node.val) {
            this.searchAndDeleteNode(node.left, node, key);
        } else { // 找到
            this.delete(node, parent);
        }
    }

    private void delete(TreeNode node, TreeNode parent) {
        // 假设是根节点，并且左右节点不存在，直接删除
        if (node.left == null && node.right == null) {
            if (node == this.root) {
                this.root = null;
            } else {
                this.removeNode(parent, parent.left == node);
            }
            return;
        }

        // 从左子树拿最最左的节点
        TreeNode foundNode = this.findAndRemoveMaxNode(node.left, node, true);
        if (foundNode != null) {
            if (node.left == null) { // 如果是直接节点
                node.left = foundNode.left;
            }
            node.val = foundNode.val;
            return;
        }

        // 从右子树拿取最左的节点
        foundNode = this.findAndRemoveMinNode(node.right, node, false);
        assert foundNode != null;
        if (node.right == null) { // 如果是直接节点
            node.right = foundNode.right;
        }
        node.val = foundNode.val;
    }

    private TreeNode findAndRemoveMinNode(TreeNode node, TreeNode parent, boolean parentLeft) {
        if (node == null) {
            return null;
        }
        // 存在左节点
        if (node.left != null) {
            return this.findAndRemoveMinNode(node.left, node, true);
        }

        // 不存在左节点，说明是最小节点
        this.removeNode(parent, parentLeft);
        return node;
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
            System.out.println(new Solution02().deleteNode(node, 0));
        }
        if (false) {
            TreeNode node = TreeNode.create(8,0,31,null,6,28,45,1,7,25,30,32,49,null,4,null,null,9,26,29,null,null,42,47,null,2,5,null,12,null,27,null,null,41,43,46,48,null,3,null,null,10,19,null,null,33,null,null,44,null,null,null,null,null,null,null,11,18,20,null,37,null,null,null,null,14,null,null,22,36,38,13,15,21,24,34,null,null,39,null,null,null,16,null,null,23,null,null,35,null,40,null,17);
            System.out.println(new Solution02().deleteNode(node, 1));

        }
    }

}
