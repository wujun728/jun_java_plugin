package cn.iocoder.algorithm.leetcode.no0113;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/path-sum-ii/
 */
public class Solution {

    private List<String> results = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if (root != null) {
            this.pathSum(root, null, sum);
        }

        // 将字符串拆解
        if (results.isEmpty()) {
            return Collections.emptyList();
        }
        List<List<Integer>> paths = new ArrayList<>();
        for (String str : results) {
            List<Integer> path = new LinkedList<>();
            Arrays.stream(str.split(",")).forEach(s -> path.add(Integer.valueOf(s)));
            paths.add(path);
        }
        return paths;
    }

    private void pathSum(TreeNode node, String result, int sum) {
        if (node == null) {
            return;
        }

        sum -= node.val;
        if (sum == 0 && node.left == null && node.right == null) {
            results.add(this.append(result, node.val));
            return;
        }

        result = this.append(result, node.val);
        this.pathSum(node.left, result, sum);
        this.pathSum(node.right, result, sum);
    }

    private String append(String result, int val) {
        if (result == null) {
            return String.valueOf(val);
        }
        return result + "," + val;
    }

}
