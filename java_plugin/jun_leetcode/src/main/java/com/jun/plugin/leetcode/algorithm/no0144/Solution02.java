package com.jun.plugin.leetcode.algorithm.no0144;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.jun.plugin.leetcode.algorithm.common.TreeNode;

/**
 * 迭代
 */
public class Solution02 {

    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<Integer> results = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            results.add(node.val);
            // 添加右、左节点
            if (node.right != null) {
                stack.add(node.right);
            }
            if (node.left != null) {
                stack.add(node.left);
            }
        }

        return results;
    }

}
