package com.jun.plugin.leetcode.algorithm.no0112;

import java.util.Stack;

import com.jun.plugin.leetcode.algorithm.common.TreeNode;

/**
 * 栈 + DFS
 */
public class Solution02 {

    private class Item {

        private TreeNode node;
        private int remain;

        public Item(TreeNode node, int remain) {
            this.node = node;
            this.remain = remain;
        }
    }

    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        Stack<Item> stack = new Stack<>();
        stack.push(new Item(root, sum));

        while (!stack.isEmpty()) {
            Item item = stack.pop();
            int remain = item.remain - item.node.val;
            if (remain == 0
                && item.node.left == null
                && item.node.right == null) {
                return true;
            }
            if (item.node.left != null) {
                stack.push(new Item(item.node.left, remain));
            }
            if (item.node.right != null) {
                stack.push(new Item(item.node.right, remain));
            }
        }

        return false;
    }

}
