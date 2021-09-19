package com.jun.plugin.leetcode.algorithm.no0653;

import java.util.ArrayList;
import java.util.List;

import com.jun.plugin.leetcode.algorithm.common.TreeNode;

/**
 * 生成有序数组 + 双指针
 */
public class Solution03 {

    public boolean findTarget(TreeNode root, int k) {
        // 创建数组
        List<Integer> numbers = new ArrayList<>();
        this.dfs(root, numbers);

        // 寻找
        int i = 0, j = numbers.size() - 1;
        while (i < j) {
            int sum = numbers.get(i) + numbers.get(j);
            if (sum == k) {
                return true;
            }
            if (sum > k) {
                j--;
            } else {
                i++;
            }
        }

        return false;
    }

    private void dfs(TreeNode node, List<Integer> numbers) {
        if (node == null) {
            return;
        }
        this.dfs(node.left, numbers);
        numbers.add(node.val);
        this.dfs(node.right, numbers);
    }

}
