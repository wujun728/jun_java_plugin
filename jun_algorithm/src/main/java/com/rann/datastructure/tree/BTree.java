package com.rann.datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Lemonjing on 2016/3/17 0017.
 * Github: Lemonjing
 * 二叉树遍历算法（递归非递归）
 */
public class BTree {

    public Node root;

    private class Node {
        private int data;
        private Node leftChild;
        private Node rightChild;

        public Node(int data) {
            this.leftChild = null;
            this.rightChild = null;
            this.data = data;
        }
    }

    /**
     * 空树
     */
    public BTree() {
        root = null;
    }

    /**
     * 创建二叉树
     *
     * @param node
     * @param data
     */
    public void buildBTree(Node node, int data) {
        if (root == null) {
            root = new Node(data);
            return;
        } else {
            if (data < node.data) {
                if (node.leftChild == null) {
                    node.leftChild = new Node(data);
                } else {
                    buildBTree(node.leftChild, data);
                }
            } else {
                if (node.rightChild == null) {
                    node.rightChild = new Node(data);
                } else {
                    buildBTree(node.rightChild, data);
                }
            }
        }
    }

    /**
     * 前序
     *
     * @param node
     */
    public void preOrder(Node node) {
        if (node != null) {
            System.out.println(node.data);
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
    }

    /**
     * 前序非递归
     *
     * @param node
     */
    public void preOrder2(Node node) {
        Stack<Node> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                System.out.println(node.data);
                node = node.leftChild;
            }
            node = stack.pop();
            node = node.rightChild;
        }
    }

    /**
     * 中序
     *
     * @param node
     */
    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.leftChild);
            System.out.println(node.data);
            inOrder(node.rightChild);
        }
    }

    /**
     * 中序非递归
     *
     * @param node
     */
    public void inOrder2(Node node) {
        Stack<Node> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.leftChild;
            }
            node = stack.pop();
            System.out.println(node.data);
            node = node.rightChild;
        }
    }

    /**
     * 后序
     *
     * @param node
     */
    public void postOrder(Node node) {
        if (node != null) {
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            System.out.println(node.data);
        }
    }

    /**
     * 后序非递归
     *
     * @param node
     */
    public void postOrder2(Node node) {
        Stack<Node> stack = new Stack<>();
        Node flagNode = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.leftChild;
            }
            node = stack.peek();
            if (node.rightChild == null || node.rightChild == flagNode) {
                System.out.println(node.data);
                flagNode = node;
                stack.pop();
                node = null;
            } else {
                node = node.rightChild;
            }
        }
    }

    /**
     * 层序遍历
     *
     * @param node
     */
    public void levelOrder(Node node) {
        if (node != null) {
            Queue<Node> q = new LinkedList<Node>();
            q.offer(node);
            while (!q.isEmpty()) {
                Node temp = q.poll();
                System.out.println(temp.data);
                if (temp.leftChild != null) {
                    q.offer(temp.leftChild);
                }
                if (temp.rightChild != null) {
                    q.offer(temp.rightChild);
                }
            }
        }
    }
}
