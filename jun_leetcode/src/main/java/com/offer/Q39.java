package com.offer;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;


/**
 * 平衡二叉树
 * 1
 * 2  3
 * 4   5   6
 * 7
 *
 * @author BaoZhou
 * @date 2020-6-16
 */

public class Q39 {
    @Test
    public void result() {
        TreeNode root = TreeWrapper.stringToTreeNode("[1,2,3,4,5,null,6,null,null,7]");
        //TreeNode root = TreeWrapper.stringToTreeNode("[1]");
        System.out.println(IsBalanced_Solution(root));
    }


    public boolean IsBalanced_Solution(TreeNode root) {
        return deepOfTree(root) != -1;
    }

    private int deepOfTree(TreeNode node) {
        if (node == null) return 0;
        int a = deepOfTree(node.left);
        int b = deepOfTree(node.right);
        if (a == -1 || b == -1 || Math.abs(a - b) > 1) return -1; //根据定义，左子树不是AVLTree或右子树不是AVLTree或自己本身就算不是AVLTree
        else return (Math.max(a, b)) + 1;
    }
}



