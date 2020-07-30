package cn.iocoder.algorithm.leetcode.no0653;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/two-sum-iv-input-is-a-bst/
 *
 * 哈希表
 */
public class Solution {

    private Set<Integer> numbers = new HashSet<>();

    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }

        // 左节点
        if (this.findTarget(root.left, k)) {
            return true;
        }

        // 当前节点
        if (numbers.contains(k - root.val)) {
            return true;
        }
        numbers.add(root.val);

        // 右节点
        return this.findTarget(root.right, k);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode node = TreeNode.create(2,0,3,-4,1);
        System.out.println(solution.findTarget(node, -1));
    }

}
