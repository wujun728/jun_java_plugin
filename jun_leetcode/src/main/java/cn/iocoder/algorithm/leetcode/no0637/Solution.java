package cn.iocoder.algorithm.leetcode.no0637;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/average-of-levels-in-binary-tree/
 */
public class Solution {

    public List<Double> averageOfLevels(TreeNode root) {
        assert root != null;

        // 初始
        List<Double> results = new ArrayList<>();
        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);

        // 遍历
        int index = 0;
        while (index < nodes.size()) {
            int length = nodes.size();
            double sum = 0;
            int count = length - index;
            for (; index < length; index++) {
                // 求和
                TreeNode node = nodes.get(index);
                sum += node.val;
                // 添加到 nodes 中
                if (node.left != null) {
                    nodes.add(node.left);
                }
                if (node.right != null) {
                    nodes.add(node.right);
                }
            }
            results.add(sum / count);
        }

        return results;
    }

}
