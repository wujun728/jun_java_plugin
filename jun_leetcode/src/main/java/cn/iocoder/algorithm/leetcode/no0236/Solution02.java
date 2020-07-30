package cn.iocoder.algorithm.leetcode.no0236;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 检索 root 到 p 和 q 的两条路径
 * 然后比对路径，或者最深的。
 */
public class Solution02 {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // p 节点的路径
        List<TreeNode> pNodes = new ArrayList<>();
        search(root, p, pNodes, new AtomicBoolean());
        // q 节点的路径
        List<TreeNode> qNodes = new ArrayList<>();
        search(root, q, qNodes, new AtomicBoolean());

        // 倒序，对比
        for (TreeNode node : pNodes) {
            for (TreeNode node2 : qNodes) {
                if (node.val == node2.val) {
                    return node;
                }
            }
        }
        return null;
    }

    private void search(TreeNode root, TreeNode target, List<TreeNode> nodes, AtomicBoolean found) {
        if (root == null) { // 理论不存在，防御性
            return;
        }

        // 如果当前节点，就是要找的，就添加到 nodes 中
        if (root.val == target.val) {
            found.set(true);
            nodes.add(root);
            return;
        }

        // 如果不是，递归子节点
        search(root.left, target, nodes, found);
        if (!found.get()) {
            search(root.right, target, nodes, found);
        }

        // 如果子节点找到，则添加到 nodes 中
        if (found.get()) {
            nodes.add(root);
        }
    }

}
