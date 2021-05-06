package cn.iocoder.algorithm.leetcode.no0700;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/search-in-a-binary-search-tree/
 */
public class Solution {

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }
        return root.val < val ? this.searchBST(root.right, val)
                : this.searchBST(root.left, val);
    }

}
