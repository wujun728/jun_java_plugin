package com.offer;


import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 对称的二叉树
 *
 * @author BaoZhou
 * @date 2020-7-1
 */
public class Q58 {

    @Test
    public void result() {
        TreeNode root = TreeWrapper.stringToTreeNode("[1,2,2,4,3,3,4]");
        System.out.println(isSymmetrical(root));
    }

    boolean isSymmetrical(TreeNode pRoot) {
        return isSame(pRoot, pRoot);
    }


    boolean isSame(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null) return false;
        if (root2 == null) return false;
        return root1.val == root2.val && isSame(root1.left, root2.right) && isSame(root1.right, root2.left);
    }
}
