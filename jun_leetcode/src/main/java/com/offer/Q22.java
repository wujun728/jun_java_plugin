package com.offer;


import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;
import java.util.*;

/**
 * 从上往下打印二叉树
 * @author BaoZhou
 * @date 2020-6-3
 */

public class Q22 {


    @Test
    public void result() {
        TreeNode head = TreeWrapper.stringToTreeNode("[1,2,3,4,5,6,7,8,9]");

        System.out.println(PrintFromTopToBottom(head).toString());
    }


    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        ArrayList<Integer> res = new ArrayList<>();

        if(root == null)
            return res;

        deque.add(root);

        while(!deque.isEmpty()){
            TreeNode node = deque.getFirst();
            deque.pollFirst();
            res.add(node.val);

            if(node.left != null)
                deque.addLast(node.left);

            if(node.right != null)
                deque.addLast(node.right);
        }

        return res;
    }
}
