package com.offer;


import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

/**
 * 二叉树的镜像
 *
 * @author BaoZhou
 * @date 2020-6-3
 */

public class Q18 {


    @Test
    public void result() {
        TreeNode head = TreeWrapper.stringToTreeNode("[1,2,3,4,5,6,7,8,9]");
        Mirror(head);
        TreeWrapper.prettyPrintTree(head);
    }


    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        if (root.left != null) {
            Mirror(root.left);
        }

        if (root.right != null) {
            Mirror(root.right);
        }
    }
}
