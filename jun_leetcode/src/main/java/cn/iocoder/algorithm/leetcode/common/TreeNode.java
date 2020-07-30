package cn.iocoder.algorithm.leetcode.common;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int x) { val = x; }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    public static TreeNode create(Integer... values) {
        if (values.length == 0) {
            return null;
        }

        List<TreeNode> nodes = new ArrayList<>(values.length + 1);
        nodes.add(null);
        for (int i = 1; i <= values.length; i++) {
            Integer value = values[i - 1];
            if (value == null) {
                continue;
            }
            TreeNode node = new TreeNode(value);
            nodes.add(node);
            // 非根节点，添加到父节点下
            if (i > 1) {
                TreeNode parent = nodes.get(i / 2);
                if (i % 2 == 0) {
                    parent.left = node;
                } else {
                    parent.right = node;
                }
            }
        }

        return nodes.get(1);
    }

}
