package cn.iocoder.algorithm.leetcode.common;

import java.util.List;

/**
 * 多节点
 */
public class Node {

    public int val;
    public List<Node> children;

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, List<Node> children) {
        this.val = val;
        this.children = children;
    }

}
