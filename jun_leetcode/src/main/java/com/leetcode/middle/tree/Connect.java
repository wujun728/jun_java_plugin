package com.leetcode.middle.tree;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 每个节点的右向指针
 *
 * @author BaoZhou
 * @date 2019/1/2
 */
public class Connect {
    public static void main(String[] args) {
        TreeLinkNode zero = new TreeLinkNode(0);
        TreeLinkNode one = new TreeLinkNode(1);
        TreeLinkNode two = new TreeLinkNode(2);
        TreeLinkNode three = new TreeLinkNode(3);
        TreeLinkNode four = new TreeLinkNode(4);
        TreeLinkNode five = new TreeLinkNode(5);
        TreeLinkNode six = new TreeLinkNode(6);
        TreeLinkNode seven = new TreeLinkNode(7);
        zero.left = one;
        zero.right = two;
        one.left = three;
        one.right = four;
        two.left = five;
        two.right = six;
        three.left = seven;

        connect(zero);
        System.out.println(one.next.val);
        System.out.println(two.next.val);
        System.out.println(three.next.val);
        System.out.println(four.next.val);
        System.out.println(five.next.val);

    }

    /**
     * 递归
     * @param root
     */
    public void connect2(TreeLinkNode root) {
        if (root == null) return;
        if (root.left != null) root.left.next = root.right;
        if (root.right != null) root.right.next = (root.next == null) ? null : root.next.left;
        connect(root.left);
        connect(root.right);
    }

    /**
     * 迭代
     * @param root
     */
    public static void connect(TreeLinkNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeLinkNode> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() != 0) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                TreeLinkNode node = queue.poll();
                node.next = i == queueSize - 1 ? null : queue.peek();
                //最后一个点指向空
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
    }


}
