package cn.iocoder.algorithm.leetcode.no0687;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/longest-univalue-path/
 *
 * 有点暴力。重复遍历过多，在 {@link Solution02} 优化
 */
public class Solution {

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(this.doLongestUnivaluePath0(root),
                Math.max(this.longestUnivaluePath(root.left),
                        this.longestUnivaluePath(root.right)));
    }

    private int doLongestUnivaluePath0(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // 左子树
        int leftCounts = node.left != null && node.left.val == node.val ?
                1 + this.doLongestUnivaluePath2(node.left) : 0;
        // 右子树
        int rightCounts = node.right != null && node.right.val == node.val ?
                1 + this.doLongestUnivaluePath2(node.right) : 0;

        // 合并
        return leftCounts + rightCounts;
    }

    private int doLongestUnivaluePath2(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftCounts = node.left != null && node.left.val == node.val ?
                1 + this.doLongestUnivaluePath2(node.left) : 0;
        int rightCounts = node.right != null && node.right.val == node.val ?
                1 + this.doLongestUnivaluePath2(node.right) : 0;

        return Math.max(leftCounts, rightCounts);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        if (true) {
            TreeNode node = TreeNode.create(5, 4, 5, 1, 1, null, 5);
            System.out.println(solution.longestUnivaluePath(node)); // 2
        }
        if (true) {
            TreeNode node = TreeNode.create(5, 4, 5, 1, 1, 5);
            System.out.println(solution.longestUnivaluePath(node)); // 2
        }
        if (true) {
            TreeNode node = TreeNode.create(1, null, 1, 1,1,1,1,1);
            System.out.println(solution.longestUnivaluePath(node)); // 4
        }
        if (true) {
            TreeNode node = TreeNode.create(1,4,5,4,4,5);
            System.out.println(solution.longestUnivaluePath(node)); // 2
        }
    }

//    public int longestUnivaluePath(TreeNode node, TreeNode parent, int sum) {
//        // 空的情况
//        if (node == null) {
//            return sum;
//        }
//
//        // 判断是否增加 sum
//        if (parent != null && node.val == parent.val) {
//            sum++;
//        } else {
//            sum = 0;
//        }
//
//
//    }

}
