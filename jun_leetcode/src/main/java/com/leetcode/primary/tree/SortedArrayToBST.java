package com.leetcode.primary.tree;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;

/**
 * 将有序数组转换为二叉搜索树
 * 思路：二分法
 *
 * @author BaoZhou
 * @date 2018/12/17
 */
public class SortedArrayToBST {
    public static void main(String[] args) {
        int[] nums = {-10, -3, 0, 5, 9};
        TreeWrapper.prettyPrintTree(sortedArrayToBST(nums));
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        return sort(nums, 0, nums.length - 1);
    }

    public static TreeNode sort(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = (left + right + 1) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = sort(nums, left, mid - 1);
        node.right = sort(nums, mid + 1, right);
        return node;
    }
}
