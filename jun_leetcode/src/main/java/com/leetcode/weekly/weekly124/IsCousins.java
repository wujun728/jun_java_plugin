package com.leetcode.weekly.weekly124;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * 二叉树的堂兄弟节点
 *
 * @author: BaoZhou
 * @date : 2019/2/17 11:45
 */
public class IsCousins {

    @Test
    public void test() {

        TreeNode root = TreeWrapper.stringToTreeNode("[1,2,3,null,4,null,5]");
        System.out.println(isCousins(root, 5, 4));
    }

    public boolean isCousins(TreeNode root, int x, int y) {
        return findX(root, x, root.val, 0).equals(findX(root, y, root.val, 0));
    }

    public ParentAndHeight findX(TreeNode node, int x, int parent, int height) {
        if (node == null) {
            return null;
        } else if (node.val == x) {
            return new ParentAndHeight(parent,node.val, height);
        } else {
            ParentAndHeight left = findX(node.left, x, node.val, height + 1);
            ParentAndHeight right = findX(node.right, x, node.val, height + 1);
            if (left != null && left.value == x) {
                return left;
            } else if (right != null && right.value == x) {
                return right;
            } else {
                return null;
            }
        }
    }

    public class ParentAndHeight {
        public int parent;
        public int value;
        public int height;

        public ParentAndHeight(int parent, int value, int height) {
            this.parent = parent;
            this.value = value;
            this.height = height;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ParentAndHeight that = (ParentAndHeight) o;
            return parent != that.parent &&
                    height == that.height;
        }

        @Override
        public int hashCode() {
            return Objects.hash(parent, height);
        }
    }
}
