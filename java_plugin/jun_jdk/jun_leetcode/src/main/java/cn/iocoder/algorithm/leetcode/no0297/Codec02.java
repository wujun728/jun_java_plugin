package cn.iocoder.algorithm.leetcode.no0297;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * 在 {@link Codec} 的基础上，将 serialize 方法的 str 参数，改成 list
 */
public class Codec02 {

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
        List<Integer> array = new LinkedList<>();
        this.serialize(root, array);

        StringBuilder sb = new StringBuilder();
        Integer first = array.remove(0);
        sb.append(first == null ? "," : first);
        for (Integer val : array) {
            if (val == null) {
                sb.append(',');
            } else {
                sb.append(',').append(val);
            }
        }
        return sb.toString();
    }

    public void serialize(TreeNode root, List<Integer> array) {
        if (root == null) {
            array.add(null);
            return;
        }
        array.add(root.val);

        // 左节点
        this.serialize(root.left, array);
        // 右节点
        this.serialize(root.right, array);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        return this.deserialize(data, new IntObject());
    }

    private TreeNode deserialize(String data, IntObject intObject) {
        int index = intObject.get();
        if (index >= data.length()) {
            return null;
        }

        // 节点为空，直接返回
        if (data.charAt(index) == ',') {
            intObject.incr();
            return null;
        }

        // 创建节点
        int value = 0;
        boolean negative = false;
        while (intObject.get() < data.length()) {
            char ch = data.charAt(intObject.get());
            intObject.incr();
            if (ch == ',') {
                break;
            }
            if (ch == '-') {
                negative = true;
                continue;
            }
            value = value * 10 + (ch - '0');
        }
        TreeNode node = new TreeNode(negative ? -value : value);
        node.left = this.deserialize(data, intObject);
        node.right = this.deserialize(data, intObject);
        return node;
    }

    public static void main(String[] args) {
        Codec02 codec = new Codec02();
        String result = codec.serialize(null);
        System.out.println(result);
    }

}
