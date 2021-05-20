package cn.iocoder.algorithm.leetcode.no0101;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class Solution02 {

//    public boolean isSymmetric(TreeNode root) {
//        if (root == null) {
//            return true;
//        }
//
//        Queue<TreeNode> queueA = new LinkedList<>();
//        queueA.add(root.left);
//        Queue<TreeNode> queueB = new LinkedList<>();
//        queueB.add(root.right);
//
//        while (!queueA.isEmpty() && !queueB.isEmpty()) {
//            TreeNode left = queueA.poll();
//            TreeNode right = queueB.poll();
//            if (left == null && right == null) {
//                continue;
//            }
//            if (left == null || right == null) {
//                return false;
//            }
//            if (left.val != right.val) {
//                return false;
//            }
//            queueA.add(left.right);
//            queueB.add(right.left);
//            queueA.add(left.left);
//            queueB.add(right.right);
//        }
//
//        return queueA.isEmpty() && queueB.isEmpty();
//    }

    // 进一步简化
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);

        while (!queue.isEmpty()) {
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            if (left == null && right == null) {
                continue;
            }
            if (left == null || right == null) {
                return false;
            }
            if (left.val != right.val) {
                return false;
            }
            queue.add(left.right);
            queue.add(right.left);
            queue.add(left.left);
            queue.add(right.right);
        }

        return true;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        TreeNode node = TreeNode.create(1, 2, 2, 3, 4, 4, 3);
        System.out.println(solution.isSymmetric(node));
        node = TreeNode.create(1, 2, 2, null, 3, null, 3);
        System.out.println(solution.isSymmetric(node));
    }

}
