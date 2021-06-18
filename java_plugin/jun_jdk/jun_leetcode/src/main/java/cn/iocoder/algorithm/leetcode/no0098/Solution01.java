package cn.iocoder.algorithm.leetcode.no0098;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 通过记录前一个节点，判断是否满足
 */
public class Solution01 {

    // 记录，遍历的前一个节点的值
    private Integer last = null;

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }

        // 左节点
        if (root.left != null) {
            boolean result = isValidBST(root.left);
            if (!result) {
                return false;
            }
        }

        // root 当前
        if (last != null && last >= root.val) {
            return false;
        }
        last = root.val;
        System.out.println(last);

        // 右节点
        if (root.right != null) {
            return isValidBST(root.right);
        }

        return true;
    }

    public static void main(String[] args) {
//        if (true) {
//            Solution solution = new Solution();
//            TreeNode root = new TreeNode(5);
//            root.left = new TreeNode(1);
//            root.right = new TreeNode(4);
//            root.right.left = new TreeNode(3);
//            root.right.right = new TreeNode(6);
//            System.out.println(solution.isValidBST(root));
//        }
        if (true) {
            Solution01 solution = new Solution01();
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(1);
//            root.right = new TreeNode(4);
//            root.right.left = new TreeNode(3);
//            root.right.right = new TreeNode(6);
            System.out.println(solution.isValidBST(root));
        }
    }

}
