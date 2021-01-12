package com.leetcode.middle.design;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 二叉树的序列化与反序列化
 * 使用层级遍历
 * <p>
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
public class Codec {
    @Test
    public void test() {
        TreeNode root = TreeWrapper.stringToTreeNode("[3,9,20,null,null,15,7]");
        TreeWrapper.prettyPrintTree(root);
        System.out.println(serialize(root));
        TreeNode deserialize = deserialize(serialize(root));
        TreeWrapper.prettyPrintTree(deserialize);
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() != 0) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    sb.append(Integer.toString(node.val) + ",");
                    queue.add(node.left);
                    queue.add(node.right);
                } else {
                    sb.append("n,");
                }

            }
        }
        return sb.toString().substring(0, sb.length() - 1);
    }


    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] s = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = null;
        if (s.length > 0 && !s[0].equals("n")) {
            root = new TreeNode(Integer.parseInt(s[0]));
            queue.offer(root);
        }
        int i = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (i < s.length) {
                if (s[i].equals("n")) {
                    node.left = null;
                    i++;
                } else {
                    node.left = new TreeNode(Integer.parseInt(s[i++]));
                    queue.offer(node.left);
                }
            }
            if (i < s.length) {
                if (s[i].equals("n")) {
                    node.right = null;
                    i++;
                } else {
                    node.right = new TreeNode(Integer.parseInt(s[i++]));
                    queue.offer(node.right);
                }
            }
        }
        return root;

    }
}
// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));