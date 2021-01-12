package com.offer;


import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 按之字形顺序打印二叉树
 * 1
 * 2          3
 * 4    5     6      7
 * 8 9  10 11 12 13  14  15
 *
 * @author BaoZhou
 * @date 2020-7-1
 */
public class Q59 {

    @Test
    public void result() {
        TreeNode root = TreeWrapper.stringToTreeNode("[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]");
        System.out.println(Arrays.toString(Print(root).toArray()));
    }

    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (pRoot == null) {
            return result;
        }
        LinkedList<TreeNode> left2right = new LinkedList<>();
        LinkedList<TreeNode> right2left = new LinkedList<>();
        left2right.offer(pRoot);
        boolean isLeft2Right = true;
        while (!left2right.isEmpty() || !right2left.isEmpty()) {
            ArrayList<Integer> temp = new ArrayList<>();
            if (isLeft2Right) {
                while (!left2right.isEmpty()) {
                    TreeNode node = left2right.pollLast();
                    temp.add(node.val);
                    if (node.left != null) right2left.offer(node.left);
                    if (node.right != null) right2left.offer(node.right);
                }
                isLeft2Right = false;
            } else {
                while (!right2left.isEmpty()) {
                    TreeNode node = right2left.pollLast();
                    temp.add(node.val);
                    if (node.right != null) left2right.offer(node.right);
                    if (node.left != null) left2right.offer(node.left);
                }
                isLeft2Right = true;
            }
            result.add(temp);
        }
        return result;
    }
}
