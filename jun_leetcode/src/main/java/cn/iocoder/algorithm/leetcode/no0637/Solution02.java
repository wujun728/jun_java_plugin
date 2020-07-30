package cn.iocoder.algorithm.leetcode.no0637;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 在 {@link Solution} 的基础上，换成队列
 *
 * 相比来说，空间慢一些，空间小一些。
 */
public class Solution02 {

    public List<Double> averageOfLevels(TreeNode root) {
        assert root != null;

        // 初始
        List<Double> results = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        // 遍历
        while (!queue.isEmpty()) {
            double sum = 0;
            int count = queue.size();
            for (int i = 0; i < count; i++) {
                // 求和
                TreeNode node = queue.poll();
                sum += node.val;
                // 添加到 nodes 中
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            results.add(sum / count);
        }

        return results;
    }

}
