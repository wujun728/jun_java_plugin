package com.jun.plugin.designpatterns.结构型模式.组合模式;

import java.util.Enumeration;
import java.util.Vector;


public class TreeNode {

    private String name; //节点名称
    private TreeNode parent; //父节点
    private Vector<TreeNode> children = new Vector<TreeNode>(); //子节点

    public TreeNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    // 添加孩子节点
    public void add(TreeNode node) {
        children.add(node);
    }

    // 删除孩子节点
    public void remove(TreeNode node) {
        children.remove(node);
    }

    // 取得孩子节点
    public Enumeration<TreeNode> getChildren() {
        return children.elements();
    }
}