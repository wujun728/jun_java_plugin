package org.typroject.tyboot.core.foundation.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * 将列表结构的数据转换为树状结构
 */
public class TreeBuilder {
    public TreeBuilder() {
    }

    /**
     * 多层循环实现
     * @param treeNodes  列表结构的数据
     * @param root 树结构的根节点标识
     * @param <N>
     * @return
     */
    public static <N extends TreeNode> Collection<N> bulid(Collection<N> treeNodes, Object root) {
        Collection<N> trees = new TreeSet<>();
        Iterator var3 = treeNodes.iterator();

        while (var3.hasNext()) {
            N treeNode = (N) var3.next();
            if (root.equals(treeNode.getMyParentId())) {
                trees.add(treeNode);
            }
            Iterator var5 = treeNodes.iterator();
            while (var5.hasNext()) {
                N it = (N) var5.next();
                if (it.getMyParentId().equals(treeNode.getMyId())) {
                    if (ValidationUtil.isEmpty(treeNode.getChildren())) {
                        treeNode.setChildren(new TreeSet<>());
                    }

                    treeNode.getChildren().add(it);
                }
            }
        }

        return trees;
    }


    /**
     * 递归方式实现
     * @param treeNodes  列表结构的数据
     * @param root   树结构的根节点标识
     * @param <N>
     * @return
     */
    public static <N extends TreeNode> Collection<N> buildByRecursive(Collection<N> treeNodes, Object root) {
        Collection<N> trees = new  TreeSet<>();
        Iterator var3 = treeNodes.iterator();
        while (var3.hasNext()) {
            N treeNode = (N) var3.next();
            if (root.equals(treeNode.getMyParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }

        return trees;
    }

    private static <N extends TreeNode> N findChildren(N node, Collection<N> treeNodes) {
        Iterator var2 = treeNodes.iterator();
        while (var2.hasNext()) {
            N it = (N) var2.next();
            if (node.getMyId().equals(it.getMyParentId())) {
                if (ValidationUtil.isEmpty(node.getChildren())) {
                    node.setChildren(new TreeSet());
                }
                node.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return node;
    }
}