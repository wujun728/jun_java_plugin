package cn.iocoder.algorithm.leetcode.no0297;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree/
 *
 * DFS 前序遍历，性能贼差
 */
public class Codec {

    private class IntObject  {

        public int index;

        public void incr() {
            index++;
        }

        public int get() {
            return index;
        }

    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return this.serialize(root, "");
    }

    public String serialize(TreeNode root, String str) {
        if (str.length() > 0) {
            str += ",";
        }
        if (root == null) {
            return str + "null";
        }
        str += root.val;

        // 左节点
        str = this.serialize(root.left, str);
        // 右节点
        str = this.serialize(root.right, str);
        return str;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // 空的情况
        if (data == null || data.length() == 0) {
            return null;
        }

        String[] datas = data.split(",");
        return this.deserialize(datas, new IntObject());
    }

    private TreeNode deserialize(String[] datas, IntObject intObject) {
        int index = intObject.get();
        if (index >= datas.length) {
            return null;
        }
        intObject.incr(); // 递增

        // 节点为空，直接返回
        String data = datas[index];
        if (data.equals("null")) {
            return null;
        }

        // 创建节点
        TreeNode node = new TreeNode(Integer.parseInt(data));
        node.left = this.deserialize(datas, intObject);
        node.right = this.deserialize(datas, intObject);
        return node;
    }

}
