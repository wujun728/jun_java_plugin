package cn.iocoder.algorithm.leetcode.no0109;

import cn.iocoder.algorithm.leetcode.common.ListNode;
import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 在 {@link Solution} 上，略微简化代码。
 */
public class Solution02 {

    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        // 链表只有一个节点，直接返回
        if (head.next == null) {
            return new TreeNode(head.val);
        }

        ListNode middlePrev = this.findMiddlePrev(head);
        ListNode middle = middlePrev.next;
        // 断开链表
        middlePrev.next = null;
        // 创建节点
        TreeNode node = new TreeNode(middle.val);

        // 左右子树
        node.left = this.sortedListToBST(head);
        node.right = this.sortedListToBST(middle.next);

        return node;
    }

    /**
     * 寻找中间点的前一个节点，通过快慢指针
     *
     *
     *
     * @param head 头节点
     * @return 中间点的前一个节点
     */
    private ListNode findMiddlePrev(ListNode head) {
        ListNode slowPre = null;
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            // 记录上一个 slow 的前一个节点
            slowPre = slow;
            // 快慢走
            slow = slow.next;
            fast = fast.next.next;
        }

        return slowPre == null ? head : slowPre;
    }

}
