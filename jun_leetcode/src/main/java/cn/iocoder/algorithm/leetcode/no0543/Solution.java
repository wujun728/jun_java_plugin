package cn.iocoder.algorithm.leetcode.no0543;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/diameter-of-binary-tree/
 */
public class Solution {

    private int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return max;
        }

        calDepth(root, 0);

        return max;
    }

    private int calDepth(TreeNode node, int depth) {
        if (node == null) {
            return depth;
        }

        int leftDepth = this.calDepth(node.left, depth + 1);
        int rightDepth = this.calDepth(node.right, depth + 1);

        // 求最大值
        // 这里 2 * depth 的原因，因为不一定是从根节点
        // 这了 -2 的原因，一个 -1 是当前 node 会计算 2 遍，另一个 -1 是算边而不是节点。
        this.max = Math.max(this.max, leftDepth + rightDepth - 2 * depth - 2);

        return Math.max(leftDepth, rightDepth);
    }

    public static void main(String[] args) {
        if (false) {
            Solution solution = new Solution();
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(2);
            root.right = new TreeNode(3);
            root.left.left = new TreeNode(4);
            root.left.right = new TreeNode(5);
            System.out.println(solution.diameterOfBinaryTree(root));
        }
        if (false) {
            Solution solution = new Solution();
            TreeNode root = new TreeNode(1);
            System.out.println(solution.diameterOfBinaryTree(root));
        }
        if (false) {
            Solution solution = new Solution();
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(2);
            System.out.println(solution.diameterOfBinaryTree(root));
        }
        if (true) {
            Solution solution = new Solution();
            TreeNode root = new TreeNode(4);
            root.left = new TreeNode(2);
            root.left.left = new TreeNode(1);
            root.left.right = new TreeNode(3);
            System.out.println(solution.diameterOfBinaryTree(root));
        }
    }

}
