package com.leetcode.middle.tree;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 中序遍历二叉树
 *
 * @author BaoZhou
 * @date 2018 /12/25
 */
public class InorderTraversal {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode("[3,9,20,null,null,15,7]");
        TreeWrapper.prettyPrintTree(root);
        System.out.println(inorderTraversal(root).toString());
    }


    /**
     * Inorder traversal list.
     *
     * @param root the root
     * @return the list
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        //List<Integer> list = new ArrayList<>();
        //if (root != null) {
        //    inorder(list, root);
        //}
        //return list;

        return inorder2(root);
    }


    /**
     * 递归
     *
     * @param list the list
     * @param node the node
     * @return
     */
    public static void inorder(List<Integer> list, TreeNode node) {
        if (node.left != null) {
            inorder(list, node.left);
        }
        list.add(node.val);
        if (node.right != null) {
            inorder(list, node.right);
        }
    }

    /**
     * 迭代
     *
     * @param node the node
     * @return list
     */
    public static List<Integer> inorder2(TreeNode node) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || node != null) {
            if(node !=null){
                stack.push(node);
                node = node.left;
            }else{
                node = stack.pop();
                list.add(node.val);
                node = node.right;
            }
        }
        return list;
    }
}
