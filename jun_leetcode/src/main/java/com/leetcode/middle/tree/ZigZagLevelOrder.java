package com.leetcode.middle.tree;

import com.leetcode.leetcodeutils.PrintUtils;
import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;

import java.util.*;

/**
 * 二叉树的锯齿形层次遍历
 *
 * @author BaoZhou
 * @date 2018/12/17
 */
public class ZigZagLevelOrder {
    public static void main(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode("[3,9,20,null,null,15,7]");
        TreeWrapper.prettyPrintTree(root);
        PrintUtils.printArrays(ZigZagLevelOrder(root));
    }

    public static List<List<Integer>> ZigZagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        boolean isFromLeft = true;
        while (queue.size() != 0) {
            List<Integer> levelList = new ArrayList<>();
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                TreeNode node;
                if (isFromLeft) {
                    node = queue.pollFirst();
                    if (node.left != null) {
                        queue.addLast(node.left);
                    }
                    if (node.right != null) {
                        queue.addLast(node.right);
                    }
                } else {
                    node = queue.pollLast();
                    if (node.right != null) {
                        queue.addFirst(node.right);
                    }
                    if (node.left != null) {
                        queue.addFirst(node.left);
                    }

                }
                levelList.add(node.val);
            }
            isFromLeft = !isFromLeft;
            result.add(levelList);
        }
        return result;
    }


}
