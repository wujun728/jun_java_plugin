package cn.iocoder.algorithm.leetcode.no0669;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/trim-a-binary-search-tree/
 */
public class Solution {

    public TreeNode trimBST(TreeNode root, int L, int R) {
        if (root == null) {
            return null;
        }
        // 满足条件，则过滤左右子树
        if (root.val <= R
            && root.val >= L) {
            root.left = this.trimBST(root.left, L, R);
            root.right = this.trimBST(root.right, L, R);
            return root;
        }
        // 节点大于最大值，则返回左子树，进一步缩小。
        if (root.val > R) {
            return this.trimBST(root.left, L, R);
        }
        // 节点大于最大值，则返回右子树，进一步扩大。
        return this.trimBST(root.right, L, R);
    }

}
