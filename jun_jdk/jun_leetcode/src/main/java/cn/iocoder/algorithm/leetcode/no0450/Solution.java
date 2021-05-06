package cn.iocoder.algorithm.leetcode.no0450;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/delete-node-in-a-bst/
 *
 * 失败的代码，越写越乱，在优化调整下。
 */
public class Solution {

    private TreeNode root;

    public TreeNode deleteNode(TreeNode root, int key) {
        this.root = root;
        this.search(root, null, key);

        return this.root;
    }

    private void search(TreeNode node, TreeNode parent, int key) {
        if (node == null) {
            return;
        }

        if (key > node.val) {
            this.search(node.right, node, key);
        } else if (key < node.val) {
            this.search(node.left, node, key);
        } else { // 找到
            this.delete(node, parent);
        }
    }

    private void delete(TreeNode node, TreeNode parent) {
        // 左右节点不存在，直接删除
        if (node.left == null && node.right == null) {
            if (parent == null) {
                this.root = null;
                return;
            }
            if (parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            return;
        }
//        TreeNode parentLeft = parent != null ? parent.left : null;
//        TreeNode parentRight = parent != null ? parent.right : null;

        // 从左子树拿最最左的节点
        TreeNode foundNode = null;
        if (node.left != null) {
            foundNode = this.findAndRemoveDeepestRightNode(node.left, node, true);
            if (foundNode != null) {
                if (foundNode != node.left) {
                    node.val = foundNode.val;
                    return;
                } else {
                    if (parent == null) { // 这种情况，只有 root 节点
                        foundNode.right = this.root.right;
                        this.root = foundNode;
                    } else {
                        parent.left = foundNode;
                        foundNode.right = node.right;
                    }
                }
                return;
            }
        } else {
            if (parent == null) { // 这种情况，只有 root 节点
                this.root = node.right;
            } else {
                parent.left = node.right;
            }
        }

        // 从右子树拿取最左的节点
        foundNode = this.findAndRemoveDeepestLeftNode(node.right, node, true);
        if (foundNode != node.right) {
            node.val = foundNode.val;
        } else {
            if (parent == null) { // 这种情况，只有 root 节点
                foundNode.left = this.root.left;
                this.root = foundNode;
            } else {
                parent.right = foundNode;
                foundNode.left = node.left;
            }
        }
    }

    private TreeNode findAndRemoveDeepestLeftNode(TreeNode node, TreeNode parent, boolean first) {
        if (node == null) {
            return null;
        }
        // 存在左节点
        if (node.left != null) {
            return this.findAndRemoveDeepestLeftNode(node.left, node, false);
        }
        // 不存在左节点，但是有右节点
        if (node.right != null) {
            // 父节点指向 node 的右节点，相当于间接删除 node 节点。
            parent.left = node.right;
        } else {
            // 删除该 node 节点
            if (!first) {
                parent.left = null;
            }
        }
        return node;
    }

    private TreeNode findAndRemoveDeepestRightNode(TreeNode node, TreeNode parent, boolean first) {
        if (node == null) {
            return null;
        }
        // 存在右节点
        if (node.right != null) {
            return this.findAndRemoveDeepestRightNode(node.right, node, false);
        }
        // 不存在右节点，但是有左节点
        if (node.left != null) {
            // 父节点指向 node 的右节点，相当于间接删除 node 节点。
            parent.right = node.left;
        } else {
            // 删除该 node 节点
            if (!first) {
                parent.right = null;
            }
        }
        return node;
    }

    public static void main(String[] args) {
        if (false) {
            TreeNode node = TreeNode.create(1, null, 2);
            System.out.println(new Solution().deleteNode(node, 1));
        }
        if (false) {
            TreeNode node = TreeNode.create(0);
            System.out.println(new Solution().deleteNode(node, 0));
        }
        if (false) {
            TreeNode node = TreeNode.create(3, 1, 4, null, 2);
            System.out.println(new Solution().deleteNode(node, 3));
        }
        if (false) {
            TreeNode node = TreeNode.create(5,3,6,2,4,null,7);
            System.out.println(new Solution().deleteNode(node, 3));
        }
        if (false) {
            TreeNode node = TreeNode.create(2, 1, 3);
            System.out.println(new Solution().deleteNode(node, 2));
        }
        if (false) {
            TreeNode node = TreeNode.create(3, 1, 4, null, 2);
            System.out.println(new Solution().deleteNode(node, 1));
        }
        if (true) {
            TreeNode node = TreeNode.create(1, null, 2);
            System.out.println(new Solution().deleteNode(node, 1));
        }
    }

}
