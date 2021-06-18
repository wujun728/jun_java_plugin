package cn.iocoder.algorithm.leetcode.no0108;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/
 *
 * 递归
 */
public class Solution {

    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }

        return this.sortedArrayToBST(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBST(int[] nums, int low, int high) {
        if (low > high) {
            return null;
        }

        // 因为左右高度不能超过 1 ，所以取中间
        int mid = low + ((high - low) >> 1);
        TreeNode node = new TreeNode(nums[mid]);

        // 左右节点
        node.left = this.sortedArrayToBST(nums, low, mid - 1);
        node.right = this.sortedArrayToBST(nums, mid + 1, high);

        // 返回节点
        return node;
    }

}
