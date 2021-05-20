package cn.iocoder.algorithm.leetcode.no0113;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.*;

/**
 * 在 {@link Solution} 的基础上，将字符串改成数组。
 */
public class Solution02 {

    private List<List<Integer>> paths = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if (root != null) {
            this.pathSum(root, new LinkedList<>(), sum);
        }

        return paths;
    }

    private void pathSum(TreeNode node, List<Integer> path, int sum) {
        if (node == null) {
            return;
        }

        sum -= node.val;

        // 符合条件，添加到结果
        if (sum == 0 && node.left == null && node.right == null) {
            path = new LinkedList<>(path);
            path.add(node.val);
            paths.add(path);
            return;
        }

        // 添加 path 中
        path.add(node.val);

        this.pathSum(node.left, path, sum);
        this.pathSum(node.right, path, sum);

        // 移除出 path 中
        path.remove(path.size() - 1);
    }

}
