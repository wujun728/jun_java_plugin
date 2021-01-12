package com.leetcode.primary.tree;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 对称二叉树
 *
 * @author BaoZhou
 * @date 2018/12/12
 */
public class IsSymmetric {
    public static void main(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode("[10,5,15,null,null,6,20]");
        TreeWrapper.prettyPrintTree(root);
        System.out.println(isSymmetric(root));
    }

    /**
     * 迭代
     *
     * @param root
     * @return
     */
    public static boolean isSymmetric(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            if (left == null && right == null) {
                continue;
            }
            if (left == null || right == null) {
                return false;
            }
            if (left.val != right.val) {
                return false;
            }
            queue.add(left.left);
            queue.add(right.right);
            queue.add(left.right);
            queue.add(right.left);
        }
        return true;
    }

    /**
     * 递归
     *
     * @param root
     * @return
     */
    public static boolean isSymmetric2(TreeNode root) {
        return valid(root, root);
    }

    private static boolean valid(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        return (left.val == right.val) && valid(left.right, right.left) && valid(left.left, right.right);
    }


}
