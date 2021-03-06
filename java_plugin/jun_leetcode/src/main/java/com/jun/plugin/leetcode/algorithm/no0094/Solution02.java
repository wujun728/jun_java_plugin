package com.jun.plugin.leetcode.algorithm.no0094;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.jun.plugin.leetcode.algorithm.common.TreeNode;

/**
 * 迭代
 */
public class Solution02 {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> results = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            // 获得当前节点的左节点
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            // 获得新的当前节点
            current = stack.pop();
            results.add(current.val);
            // 获得右节点
            current = current.right;
        }

        return results;
    }

}
