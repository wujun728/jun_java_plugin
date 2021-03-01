package com.jun.plugin.designpatterns.结构型模式.组合模式;

public class Tree {

    TreeNode root = null;

    public Tree(String name) {
        root = new TreeNode(name);
    }

}