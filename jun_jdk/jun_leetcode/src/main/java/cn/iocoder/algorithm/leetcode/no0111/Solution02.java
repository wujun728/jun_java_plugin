package cn.iocoder.algorithm.leetcode.no0111;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 队列
 */
public class Solution02 {

    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        root.val = 1; // 比较粗暴，直接把 val 改成所属层级。
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left == null && node.right == null) {
                return node.val;
            }
            if (node.left != null) {
                queue.add(node.left);
                node.left.val = node.val + 1;
            }
            if (node.right != null) {
                queue.add(node.right);
                node.right.val = node.val + 1;
            }
        }

        return -1;
    }

}
