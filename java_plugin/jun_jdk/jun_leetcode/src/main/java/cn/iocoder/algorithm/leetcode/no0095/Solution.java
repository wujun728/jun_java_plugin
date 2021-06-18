package cn.iocoder.algorithm.leetcode.no0095;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/unique-binary-search-trees-ii/
 *
 * 分治算法 + 递归
 */
public class Solution {

    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return Collections.emptyList();
        }
        return this.partition(1, n);
    }

    public List<TreeNode> partition(int low, int high) {
        if (low > high) {
            return Collections.singletonList(null);
        }
        if (low == high) {
            return Collections.singletonList(new TreeNode(low));
        }

        // 遍历
        List<TreeNode> result = new ArrayList<>();
        for (int i = low; i <= high; i++) {
            for (TreeNode left : this.partition(low, i - 1)) {
                for (TreeNode right : this.partition(i + 1, high)) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    result.add(root);
                }
            }
        }
        return result;
    }

}
