package com.rann.datastructure.tree;

/**
 * Created by Lemonjing on 2016/3/17 0017.
 * Github: Lemonjing
 * email: xmusaber@163.com
 */
public class BTreeTest {
    public static void main(String[] args) {
        int[] a = {5, 8, 10, 4};
        BTree t = new BTree();

        for (int i = 0; i < a.length; i++) {
            t.buildBTree(t.root, a[i]);
        }

        System.out.println("前序遍历：");
        t.preOrder(t.root);
        System.out.println("前序非递归遍历：");
        t.preOrder2(t.root);
        System.out.println("中序遍历：");
        t.inOrder(t.root);
        System.out.println("中序非递归遍历：");
        t.inOrder2(t.root);
        System.out.println("后序遍历：");
        t.postOrder(t.root);
        System.out.println("后序非遍历：");
        t.postOrder(t.root);
        System.out.println("层序遍历：");
        t.levelOrder(t.root);
    }
}
