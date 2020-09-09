package com.rann.datastructure.tree;

/**
 * Created by Lemonjing on 2016/3/17 0017.
 * Github: Lemonjing
 * email: xmusaber@163.com
 */

public class AVLTreeTest {
    public static void main(String[] args) {
        // 插入And 遍历
        AVLTree t = new AVLTree();
        t.insert(1);
        t.insert(3);
        t.insert(4);
        t.insert(6);
        t.insert(7);

        t.traverse(t, "pre");
        t.traverse(t, "in");
        t.traverse(t, "post");

        // 打印
        t.display();
    }
}
