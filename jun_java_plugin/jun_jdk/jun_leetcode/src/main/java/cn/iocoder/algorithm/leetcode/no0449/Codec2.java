package cn.iocoder.algorithm.leetcode.no0449;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用【前序遍历】的方式，序列化和反序列化。
 */
public class Codec2 {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        List<Integer> array = new ArrayList<>();
        this.serialize(root, array);

        // 拼接字符串
        if (array.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(array.get(0));
        for (int i = 1; i < array.size(); i++) {
            sb.append(',').append(array.get(i));
        }
        return sb.toString();
    }

    private void serialize(TreeNode node, List<Integer> array) {
        if (node == null) {
            return;
        }
        array.add(node.val);
        this.serialize(node.left, array);
        this.serialize(node.right, array);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("")) {
            return null;
        }
        // 解析字符串
        List<Integer> array = new ArrayList<>();
        String[] datas = data.split(",");
        for (int i = 0; i < datas.length; i++) {
            array.add(Integer.parseInt(datas[i]));
        }

        // 解析
        return this.deserialize(array, 0, array.size() - 1);
    }

    private TreeNode deserialize(List<Integer> array, int low, int high) {
        // 创建当前节点
        if (low > high) {
            return null;
        }
        int value = array.get(low);
        TreeNode node = new TreeNode(value);

        // 寻找第一个比当前节点大的位置，说明它是当前节点的右节点。
        int index = low + 1;
        for (; index <= high; index++) {
            if (array.get(index) > value) {
                break;
            }
        }

        // 左右子树
        node.left = this.deserialize(array, low + 1, index - 1);
        node.right = this.deserialize(array, index, high);

        return node;
    }

}
